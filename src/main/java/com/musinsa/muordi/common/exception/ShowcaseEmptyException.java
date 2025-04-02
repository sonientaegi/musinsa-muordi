package com.musinsa.muordi.common.exception;

public class ShowcaseEmptyException extends BaseException {
    public ShowcaseEmptyException() {
        super("전시중이거나 전시조건에 부합하는 상품이 하나도 없습니다. 관리자에게 문의하세요.");
    }
}
