package com.yjava.protocol;

public abstract class YProtocol {

  private static final int MESSAGE_SYNC = 0;
  private static final int MESSAGE_AWARENESS = 1;
  private static final int MESSAGE_AUTH = 2;
  private static final int MESSAGE_SAVE = 3;

  private static final int MESSAGE_YJS_SYNC_STEP_1 = 0;
  private static final int MESSAGE_YJS_SYNC_STEP_2 = 1;
  private static final int MESSAGE_YJS_UPDATE = 2;

  protected final TransactionalYDoc yDoc;

  public YProtocol(TransactionalYDoc yDoc) {
    this.yDoc = yDoc;
  }

  public void handleByMessageType(Decoder decoder, Encoder encoder){
    int type = decoder.readVarUint();
    beforeHandleByMessageType(type);
    switch (type){
      case MESSAGE_SYNC -> handleMessageSync(decoder, encoder);
//      case MESSAGE_SAVE -> handleMessageSave(decoder); break;
      default -> {}
    }
    afterHandleByMessageType(type);
  }

  public void handleMessageSync(Decoder decoder, Encoder encoder){
    int subType = decoder.readVarUint();
    beforeHandleMessageSync(subType);
    switch (subType){
      case MESSAGE_YJS_SYNC_STEP_1 -> handleMessageYjsSyncStep1(decoder, encoder);
      case MESSAGE_YJS_SYNC_STEP_2 -> handleMessageYjsSyncStep2(decoder);
      case MESSAGE_YJS_UPDATE -> handleMessageYjsUpdate(decoder);
    }
    afterHandleMessageSync(subType);
  }

  public void handleMessageYjsUpdate(Decoder decoder) {
    beforeHandleMessageYjsUpdate();
    handleMessageYjsSyncStep2(decoder);
    afterHandleMessageYjsUpdate();
  }

  public void handleMessageYjsSyncStep2(Decoder decoder) {
    beforeHandleMessageYjsSyncStep2();
    byte[] updateDiff = decoder.readVarUint8Array();
    this.yDoc.applyUpdate(updateDiff);
    afterHandleMessageYjsSyncStep2();
  }

  public void handleMessageYjsSyncStep1(Decoder decoder, Encoder encoder) {
    beforeHandleMessageYjsSyncStep1();
    byte[] diff = decoder.readVarUint8Array();
    byte[] update = this.yDoc.encodeStateAsUpdate(diff);
    encoder.writeVarUint(MESSAGE_SYNC);
    encoder.writeVarUint(MESSAGE_YJS_SYNC_STEP_2);
    encoder.writeVarUint8Array(update);
    afterHandleMessageYjsSyncStep1();
  }

  protected void beforeHandleByMessageType(int type) {}
  protected void afterHandleByMessageType(int type) {}

  protected void beforeHandleMessageSync(int subType) {}
  protected void afterHandleMessageSync(int subType) {}

  protected void beforeHandleMessageYjsUpdate() {}
  protected void afterHandleMessageYjsUpdate() {}

  protected void beforeHandleMessageYjsSyncStep1() {}
  protected void afterHandleMessageYjsSyncStep1() {}

  protected void beforeHandleMessageYjsSyncStep2() {}
  protected void afterHandleMessageYjsSyncStep2() {}

}
