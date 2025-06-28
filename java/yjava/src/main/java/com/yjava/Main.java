package com.yjava;

import com.yjava.bridge.NativeBridge;

public class Main {

  public static void main(String[] args) {
    NativeBridge test = NativeBridge.getInstance();

    // 1. 새 문서 생성
    long docPtr = test.ydocNew();
    assert docPtr != 0 : "Failed to create document";

    // 2. 쓰기 트랜잭션 시작
    long writeTxn1 = test.ydocWriteTransaction(docPtr, "origin1");
    assert writeTxn1 != 0 : "Failed to start write transaction";

    // 3. 두 번째 트랜잭션은 실패해야 함 (동시 활성 불가)
    long writeTxn2 = test.ydocWriteTransaction(docPtr, "origin2");
    assert writeTxn2 == 0 : "Second write transaction should fail";

    // 4. 읽기 트랜잭션도 실패해야 함 (쓰기 중에는 읽기 불가)
    long readTxn = test.ydocReadTransaction(docPtr);
    assert readTxn == 0 : "Read transaction during write should fail";

    // 5. 상태 벡터 추출
    byte[] stateVector = test.ytransactionStateVectorV1(writeTxn1);
    assert stateVector != null : "State vector is null";

    // 6. 상태 차이 추출 (자기 자신과 비교하면 차이 없음)
    byte[] diff = test.ytransactionStateDiffV1(writeTxn1, stateVector);
    assert diff != null : "State diff is null";

    // 7. diff를 같은 트랜잭션에 적용 (의미는 없지만 테스트)
    byte applyResult = test.ytransactionApply(writeTxn1, diff);
    assert applyResult == 1 : "Failed to apply diff";

    // 8. 트랜잭션 커밋
    test.ytransactionCommit(writeTxn1);

    // 9. 커밋 후 새 읽기 트랜잭션은 가능해야 함
    long readTxnAfterCommit = test.ydocReadTransaction(docPtr);
    assert readTxnAfterCommit != 0 : "Read transaction after commit failed";
    test.ytransactionCommit(readTxnAfterCommit);

    // 10. 문서 제거
    test.ydocDestroy(docPtr);

  }
}