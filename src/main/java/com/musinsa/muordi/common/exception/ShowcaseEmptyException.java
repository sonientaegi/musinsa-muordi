package com.musinsa.muordi.common.exception;

public class ShowcaseEmptyException extends UnavailableException {
    public ShowcaseEmptyException() {
        super("쇼케이스에 전시 중 이거나, 요청한 전시 조건을 만족한 상품이 하나도 없습니다. 관리자에게 문의하세요");
    }
}
