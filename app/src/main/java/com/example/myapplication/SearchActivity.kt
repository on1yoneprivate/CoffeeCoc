package com.example.myapplication

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

class SearchActivity: AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var parentLayout: LinearLayout

    val buttonTitles = listOf(
        //커피
        "에스프레소", "아포카토", "아메리카노", "티라미수 라떼", "꿀 아메리카노", "바닐라 라떼", "바닐라 아메리카노", "연유 라떼", "카라멜 마끼아또", "카페 라떼", "카페 모카", "카푸치노", "콜드브루 라떼", "콜드브루", "헤이즐넛 라떼", "헤이즐넛 아메리카노", "큐브 라떼", "롱블랙", "아이스크림 카페라떼", "나이트로 바닐라 크림 콜드브루", "나이트로 콜드 브루", "돌체 콜드 브루", "리저브 나이트로", "리저브 콜드 브루", "민트 콜드 브루", "바닐라 크림 콜드 브루", "시그니처 더 블랙 콜드 브루", "여수 윤슬 헤이즐넛 콜드브루", "오트 콜드 브루", "제주 비자림 콜드브루", "콜드 브루 (트렌타)", "콜드 브루 몰트", "콜드 브루 플로트", "에스프레소 콘파나", "에스프레소 마키아또", "더 그린 쑥 크림 라떼", "라벤더 카페 브레베", "브라운 슈가 오트 쉐이큰 에스프레소", "사케라또 비안코 오버 아이스", "돌체 라떼", "화이트 초콜릿 모카", "민트 모카", "바닐라 플랫 화이트", "바닐라 스타벅스 더블 샷", "블론드 바닐라 더블 샷 마키아또", "사케라또 아포카토", "스파클링 시트러스 에스프레소", "커피 스타벅스 더블 샷", "클래식 아포카토", "헤이즐넛 스타벅스 더블 샷", "꿀 라떼", "아몬드 라떼", "녹차 샷 라떼", "믹스 커피", "토피넛 샷 라떼", "바나나 달달 커피", "아인슈페너", "달고나 콜드브루 라떼", "흑임자 카페라떼", "달고나 라떼", "콜드브루 크림넛", "흑당 콜드브루", "버블 흑당 콜드브루", "연유 콜드브루", "콜드브루 화이트 비엔나", "콜드브루 니트로", "더치커피", "더치라떼", "아인슈페너 라떼", "흑당 라떼", "레몬 아메리카노", "에스프레소 마끼아또", "바닐라 라떼 마끼아또", "카페 헤이오트", "바닐라 딜라이트", "돌체 콜드브루 라떼",
        //디카페인
        "디카페인 에스프레소", "디카페인 아포카토", "에스프레소 피에노", "디카페인 아메리카노", "디카페인 꿀아메리카노", "디카페인 카페 라떼", "디카페인 카푸치노", "디카페인 바닐라 라떼", "디카페인 헤이즐넛 라떼", "디카페인 카라멜마끼아또", "디카페인 연유라떼", "디카페인 카페 모카", "디카페인 티라미수 라떼", "디카페인 헤이즐넛 아메리카노", "디카페인 바닐라 아메리카노", "디카페인 콜드브루", "디카페인 콜드브루 라떼", "디카페인 꿀 라떼", "디카페인 아몬드 라떼", "디카페인 바나나 달달 커피", "디카페인 아인슈페너", "디카페인 돌체 콜드브루 라떼", "디카페인 달고나 콜드브루 라떼", "디카페인 콜드브루 화이트 비엔나", "디카페인 콜드부르 니트로", "디카페인 흑당 콜드브루", "디카페인 연유 콜드브루", "디카페인 콜드브루 크림넛", "디카페인 버블 흑당 콜드브루", "디카페인 더치커피", "디카페인 더치라떼", "디카페인 오트 콜드브루 플로트", "디카페인 콜드브루 화이트 비엔나", "디카페인 콜드부르 니트로",
        // 논커피
        "딸기 라떼", "고구마 라떼", "곡물 라떼", "초코 라떼", "토피넛 라떼", "오레오 초코 라떼", "흑당 버블밀크티 라떼", "핫초코", "녹차 라떼", "밀크티 라떼", "흑당 라떼", "흑당 밀크티 라떼", "흑당 버블 라떼", "아이스 초코", "딸기 콜드폼 초콜릿", "플러피 판다 아이스 초콜릿", "플러피 판다 핫 초콜릿", "스타벅스 슬래머", "스팀 우유", "우유", "달고나 라떼", "밀크티", "아몬드 밀크티", "블루베리 요거트 드링크", "플레인 요거트 드링크", "쑥 라떼", "소프트 아이스크림 (컵/콘)", "버블 크림 밀크티", "민트 초콜릿", "이곡 라떼", "쿠키초코라떼", "민트초코오레오라떼", "흑당밀크", "블루베리 라떼", "망고 라떼", "딥초코버블라떼", "화이트 초코",
        // 프라페
        "딸기 쿠키 프라페", "쿠키 프라페", "녹차 프라페", "딸기퐁 크러쉬", "리얼 초코 프라페", "민트 프라페", "바나나퐁 크러쉬", "스트로베리 치즈 홀릭", "초코허니퐁 크러쉬", "커피 프라페", "슈크림 허니퐁 크러쉬", "플레인퐁 크러쉬", "유니콘 프라페", "스모어 블랙쿠키 프라페", "스모어 카라멜쿠키 프라페", "꿀복숭아 플랫치노", "망고 플랫치노", "자몽 플랫치노", "초콜렛 칩 플랫치노", "민트 초콜렛 칩 플랫치노", "플레인 요거트 플랫치노", "블루베리 요거트 플랫치노", "딸기 요거트 플랫치노",
        //에이드
        "라임 모히또", "레몬에이드", "블루레몬에이드", "자몽에이드", "청포도에이드", "유니콘매직에이드(핑크)", "유니콘매직에이드(블루)", "체리콕", "메가에이드", "스노우 샹그리아 에이드", "레드 오렌지 자몽 주스", "샤인머스캣 그린 주스", "딸기 주스", "딸기 바나나 주스", "유자에이드", "한라봉에이드", "청귤에이드", "깔라만시 에이드", "오미자 에이드", "메머드에이드", "수박 주스", "파인애플 주스", "설향 생딸기 주스", "스테이씨 틴프레쉬 에이드", "라임애플 스파클링", "망고에이드", "패션후르츠에이드", "키위주스", "복숭아주스", "샤인머스켓케일주스", "오렌지당근 주스", "샤인코코에이드", "여수 바다 자몽 피지오", "유자 패션 피지오", "쿨 라임 피지오", "피치 딸기 피지오", "필 더 그린", "필 더 레드", "필 더 옐로우", "망고주스", "스타 루비 자몽 스위트", "유기농 오렌지 100% 주스", "케일&사과 주스", "핑크 용과 레모네이드", "한라봉주스", "사과 주스", "딸기 가득 요거트", "블루베리 요거트", "오렌지 자몽 주스", "키위 바나나 주스", "생딸기 가득 주스", "오렌지에이드", "복숭아에이드", "레몬 셔벗 에이드", "샤인머스캣 청포도 에이드", "오렌지주스", "토마토주스", "오렌지 핫주스", "루비자몽 핫주스", "루비자몽 주스", "복숭아 자두 스파클링", "청포도케일 주스", "사과비트 주스", "홍시주스", "골드키위주스", "자몽네이블오렌지", "유자피나콜라다", "석류애플라임", "사과&당근 주스", "감귤&한라봉주스", "콤부차 복숭아망고", "콤부차 청포도레몬"

    )

    val coffeeShopMap = mapOf(
        "에스프레소" to listOf("스타벅스", "메가커피", "투썸", "이디야", "컴포즈", "파스쿠찌", "할리스"),
        "아포카토" to listOf("메가커피", "투썸", "이디야", "파스쿠찌", "할리스"),
        "아메리카노" to listOf("스타벅스", "메가커피", "메머드커피", "이디야", "컴포즈", "게이트", "파스쿠찌", "할리스"),
        "티라미수 라떼" to listOf("메가커피"),
        "꿀 아메리카노" to listOf("메가커피", "메머드커피"),
        "바닐라 라떼" to listOf("스타벅스", "메가커피", "메머드커피", "투썸", "이디야", "컴포즈", "게이트"),
        "바닐라 아메리카노" to listOf("메가커피"),
        "연유 라떼" to listOf("메가커피", "메머드커피", "투썸", "이디야", "할리스"),
        "카라멜 마끼아또" to listOf("스타벅스", "메가커피", "투썸", "이디야", "컴포즈", "게이트", "할리스"),
        "카페 라떼" to listOf("스타벅스", "메가커피", "메머드커피", "투썸", "이디야", "컴포즈", "게이트", "파스쿠찌", "할리스"),
        "카페 모카" to listOf("스타벅스", "메가커피", "메머드커피", "투썸", "이디야", "컴포즈", "게이트", "파스쿠찌", "할리스"),
        "카푸치노" to listOf("스타벅스", "메가커피", "투썸", "이디야", "컴포즈", "파스쿠찌", "할리스"),
        "콜드브루 라떼" to listOf("메가커피", "메머드커피", "투썸", "이디야", "게이트", "파스쿠찌", "할리스"),
        "콜드브루" to listOf("스타벅스", "메가커피", "메머드커피", "투썸", "이디야", "게이트", "파스쿠찌", "할리스"),
        "헤이즐넛 라떼" to listOf("메가커피", "메머드커피", "컴포즈"),
        "헤이즐넛 아메리카노" to listOf("메가커피", "메머드커피"),
        "큐브 라떼" to listOf("메가커피"),
        "롱블랙" to listOf("투썸"),
        "아이스크림 카페라떼" to listOf("투썸"),
        "나이트로 바닐라 크림 콜드브루" to listOf("스타벅스"),
        "나이트로 콜드 브루" to listOf("스타벅스"),
        "돌체 콜드 브루" to listOf("스타벅스", "게이트"),
        "리저브 나이트로" to listOf("스타벅스"),
        "리저브 콜드 브루" to listOf("스타벅스"),
        "민트 콜드 브루" to listOf("스타벅스"),
        "바닐라 크림 콜드 브루" to listOf("스타벅스", "게이트"),
        "시그니처 더 블랙 콜드 브루" to listOf("스타벅스"),
        "여수 윤슬 헤이즐넛 콜드브루" to listOf("스타벅스"),
        "오트 콜드 브루" to listOf("스타벅스", "파스쿠찌"),
        "제주 비자림 콜드브루" to listOf("스타벅스"),
        "콜드 브루 (트렌타)" to listOf("스타벅스"),
        "콜드 브루 몰트" to listOf("스타벅스"),
        "콜드 브루 플로트" to listOf("스타벅스"),
        "에스프레소 콘파나" to listOf("스타벅스", "이디야", "할리스"),
        "에스프레소 마키아또" to listOf("스타벅스", "이디야", "할리스"),
        "더 그린 쑥 크림 라떼" to listOf("스타벅스"),
        "라벤더 카페 브레베" to listOf("스타벅스"),
        "브라운 슈가 오트 쉐이큰 에스프레소" to listOf("스타벅스"),
        "사케라또 비안코 오버 아이스" to listOf("스타벅스"),
        "돌체 라떼" to listOf("스타벅스", "컴포즈", "게이트"),
        "화이트 초콜릿 모카" to listOf("스타벅스", "이디야"),
        "민트 모카" to listOf("스타벅스", "이디야"),
        "바닐라 플랫 화이트" to listOf("스타벅스"),
        "바닐라 스타벅스 더블 샷" to listOf("스타벅스"),
        "블론드 바닐라 더블 샷 마키아또" to listOf("스타벅스"),
        "사케라또 아포카토" to listOf("스타벅스"),
        "스파클링 시트러스 에스프레소" to listOf("스타벅스"),
        "커피 스타벅스 더블 샷" to listOf("스타벅스"),
        "클래식 아포카토" to listOf("스타벅스"),
        "헤이즐넛 스타벅스 더블 샷" to listOf("스타벅스"),
        "꿀 라떼" to listOf("메머드커피"),
        "아몬드 라떼" to listOf("메머드커피"),
        "녹차 샷 라떼" to listOf("메머드커피"),
        "믹스 커피" to listOf("메머드커피", "게이트"),
        "토피넛 샷 라떼" to listOf("메머드커피"),
        "바나나 달달 커피" to listOf("메머드커피"),
        "아인슈페너" to listOf("메머드커피", "이디야", "컴포즈"),
        "달고나 콜드브루 라떼" to listOf("메머드커피"),
        "흑임자 카페라떼" to listOf("투썸"),
        "달고나 라떼" to listOf("투썸", "컴포즈"),
        "콜드브루 크림넛" to listOf("이디야"),
        "흑당 콜드브루" to listOf("이디야"),
        "버블 흑당 콜드브루" to listOf("이디야"),
        "연유 콜드브루" to listOf("이디야", "파스쿠찌"),
        "콜드브루 화이트 비엔나" to listOf("이디야"),
        "콜드브루 니트로" to listOf("이디야"),
        "더치커피" to listOf("컴포즈"),
        "더치라떼" to listOf("컴포즈"),
        "아인슈페너 라떼" to listOf("컴포즈"),
        "흑당 라떼" to listOf("컴포즈"),
        "레몬 아메리카노" to listOf("게이트"),
        "에스프레소 마끼아또" to listOf("파스쿠찌"),
        "바닐라 라떼 마끼아또" to listOf("파스쿠찌"),
        "카페 헤이오트" to listOf("파스쿠찌"),
        "바닐라 딜라이트" to listOf("할리스"),
        "돌체 콜드브루 라떼" to listOf("메머드커피"),

        // 디카페인
        "디카페인 에스프레소" to listOf("메가커피"),
        "디카페인 아포카토" to listOf("메가커피"),
        "에스프레소 피에노" to listOf("메가커피"),
        "디카페인 아메리카노" to listOf("메가커피", "메머드커피"),
        "디카페인 꿀아메리카노" to listOf("메가커피", "메머드커피"),
        "디카페인 카페 라떼" to listOf("메가커피", "메머드커피"),
        "디카페인 카푸치노" to listOf("메가커피"),
        "디카페인 바닐라 라떼" to listOf("메가커피", "메머드커피"),
        "디카페인 헤이즐넛 라떼" to listOf("메가커피", "메머드커피"),
        "디카페인 카라멜마끼아또" to listOf("메가커피"),
        "디카페인 연유라떼" to listOf("메가커피", "메머드커피"),
        "디카페인 카페 모카" to listOf("메가커피", "메머드커피"),
        "디카페인 티라미수 라떼" to listOf("메가커피"),
        "디카페인 헤이즐넛 아메리카노" to listOf("메가커피", "메머드커피"),
        "디카페인 바닐라 아메리카노" to listOf("메가커피"),
        "디카페인 콜드브루" to listOf("메가커피", "메머드커피", "이디야", "파스쿠찌", "할리스"),
        "디카페인 콜드브루 라떼" to listOf("메가커피", "메머드커피", "이디야", "파스쿠찌", "할리스"),
        "디카페인 꿀 라떼" to listOf("메머드커피"),
        "디카페인 아몬드 라떼" to listOf("메머드커피"),
        "디카페인 바나나 달달 커피" to listOf("메머드커피"),
        "디카페인 아인슈페너" to listOf("메머드커피"),
        "디카페인 돌체 콜드브루 라떼" to listOf("메머드커피"),
        "디카페인 달고나 콜드브루 라떼" to listOf("메머드커피"),
        "디카페인 콜드브루 화이트 비엔나" to listOf("이디야"),
        "디카페인 콜드부르 니트로" to listOf("이디야"),
        "디카페인 흑당 콜드브루" to listOf("이디야", "할리스"),
        "디카페인 연유 콜드브루" to listOf("이디야", "파스쿠찌", "할리스"),
        "디카페인 콜드브루 크림넛" to listOf("이디야", "할리스"),
        "디카페인 버블 흑당 콜드브루" to listOf("이디야", "할리스"),
        "디카페인 더치커피" to listOf("컴포즈"),
        "디카페인 더치라떼" to listOf("컴포즈"),
        "디카페인 오트 콜드브루 플로트" to listOf("파스쿠찌"),
        "디카페인 콜드브루 화이트 비엔나" to listOf("할리스"),
        "디카페인 콜드부르 니트로" to listOf("할리스"),

        // 논커피
        "딸기 라떼" to listOf("스타벅스", "메가커피", "이디야", "컴포즈", "게이트"),
        "고구마 라떼" to listOf("메가커피", "메머드커피", "투썸", "게이트"),
        "곡물 라떼" to listOf("메가커피", "컴포즈", "게이트"),
        "초코 라떼" to listOf("메가커피", "메머드커피", "투썸", "컴포즈", "게이트"),
        "토피넛 라떼" to listOf("메가커피", "메머드커피", "이디야"),
        "오레오 초코 라떼" to listOf("메가커피"),
        "흑당 버블밀크티 라떼" to listOf("메가커피", "파스쿠찌"),
        "핫초코" to listOf("스타벅스", "메가커피", "이디야", "파스쿠찌", "할리스"),
        "녹차 라떼" to listOf("메가커피", "메머드커피", "이디야", "컴포즈", "게이트", "할리스"),
        "밀크티 라떼" to listOf("메가커피", "게이트"),
        "흑당 라떼" to listOf("메가커피"),
        "흑당 밀크티 라떼" to listOf("메가커피"),
        "흑당 버블 라떼" to listOf("메가커피", "이디야", "게이트", "파스쿠찌"),
        "아이스 초코" to listOf("스타벅스", "메가커피", "이디야", "파스쿠찌"),
        "딸기 콜드폼 초콜릿" to listOf("스타벅스"),
        "플러피 판다 아이스 초콜릿" to listOf("스타벅스"),
        "플러피 판다 핫 초콜릿" to listOf("스타벅스"),
        "스타벅스 슬래머" to listOf("스타벅스"),
        "스팀 우유" to listOf("스타벅스"),
        "우유" to listOf("스타벅스"),
        "달고나 라떼" to listOf("메머드커피", "이디야"),
        "밀크티" to listOf("메머드커피", "컴포즈", "할리스"),
        "아몬드 밀크티" to listOf("메머드커피"),
        "블루베리 요거트 드링크" to listOf("투썸"),
        "플레인 요거트 드링크" to listOf("투썸"),
        "쑥 라떼" to listOf("투썸"),
        "소프트 아이스크림 (컵/콘)" to listOf("투썸"),
        "버블 크림 밀크티" to listOf("이디야"),
        "민트 초콜릿" to listOf("이디야", "할리스"),
        "이곡 라떼" to listOf("이디야"),
        "쿠키초코라떼" to listOf("컴포즈"),
        "민트초코오레오라떼" to listOf("컴포즈"),
        "흑당밀크" to listOf("컴포즈"),
        "블루베리 라떼" to listOf("컴포즈"),
        "망고 라떼" to listOf("컴포즈"),
        "딥초코버블라떼" to listOf("게이트"),
        "화이트 초코" to listOf("할리스"),

        //에이드
        "라임 모히또" to listOf("메가커피"),
        "레몬에이드" to listOf("메가커피", "이디야", "컴포즈", "게이트", "파스쿠찌"),
        "블루레몬에이드" to listOf("메가커피", "메머드커피", "컴포즈"),
        "자몽에이드" to listOf("메가커피", "메머드커피", "투썸", "이디야", "컴포즈", "게이트", "파스쿠찌"),
        "청포도에이드" to listOf("메가커피", "메머드커피", "이디야", "컴포즈", "파스쿠찌", "할리스"),
        "유니콘매직에이드(핑크)" to listOf("메가커피"),
        "유니콘매직에이드(블루)" to listOf("메가커피"),
        "체리콕" to listOf("메가커피", "게이트"),
        "메가에이드" to listOf("메가커피"),
        "스노우 샹그리아 에이드" to listOf("메가커피"),
        "레드 오렌지 자몽 주스" to listOf("메가커피"),
        "샤인머스캣 그린 주스" to listOf("메가커피"),
        "딸기 주스" to listOf("스타벅스", "메가커피", "이디야"),
        "딸기 바나나 주스" to listOf("메가커피"),
        "유자에이드" to listOf("메머드커피", "컴포즈"),
        "한라봉에이드" to listOf("메머드커피"),
        "청귤에이드" to listOf("메머드커피"),
        "깔라만시 에이드" to listOf("메머드커피"),
        "오미자 에이드" to listOf("메머드커피"),
        "메머드에이드" to listOf("메머드커피"),
        "수박 주스" to listOf("메머드커피", "컴포즈"),
        "파인애플 주스" to listOf("메머드커피"),
        "설향 생딸기 주스" to listOf("컴포즈"),
        "스테이씨 틴프레쉬 에이드" to listOf("컴포즈"),
        "라임애플 스파클링" to listOf("컴포즈"),
        "망고에이드" to listOf("컴포즈"),
        "패션후르츠에이드" to listOf("컴포즈"),
        "키위주스" to listOf("컴포즈", "파스쿠찌", "할리스"),
        "복숭아주스" to listOf("컴포즈"),
        "샤인머스켓케일주스" to listOf("컴포즈"),
        "오렌지당근 주스" to listOf("컴포즈", "할리스"),
        "샤인코코에이드" to listOf("게이트"),
        "여수 바다 자몽 피지오" to listOf("스타벅스"),
        "유자 패션 피지오" to listOf("스타벅스"),
        "쿨 라임 피지오" to listOf("스타벅스"),
        "피치 딸기 피지오" to listOf("스타벅스"),
        "필 더 그린" to listOf("스타벅스"),
        "필 더 레드" to listOf("스타벅스"),
        "필 더 옐로우" to listOf("스타벅스"),
        "망고주스" to listOf("스타벅스"),
        "스타 루비 자몽 스위트" to listOf("스타벅스"),
        "유기농 오렌지 100% 주스" to listOf("스타벅스"),
        "케일&사과 주스" to listOf("스타벅스"),
        "핑크 용과 레모네이드" to listOf("스타벅스"),
        "한라봉주스" to listOf("스타벅스"),
        "사과 주스" to listOf("스타벅스", "이디야"),
        "딸기 가득 요거트" to listOf("스타벅스"),
        "블루베리 요거트" to listOf("스타벅스"),
        "오렌지 자몽 주스" to listOf("투썸"),
        "키위 바나나 주스" to listOf("투썸"),
        "생딸기 가득 주스" to listOf("투썸"),
        "오렌지에이드" to listOf("투썸"),
        "복숭아에이드" to listOf("투썸"),
        "레몬 셔벗 에이드" to listOf("투썸"),
        "샤인머스캣 청포도 에이드" to listOf("투썸"),
        "오렌지주스" to listOf("파스쿠찌"),
        "토마토주스" to listOf("파스쿠찌", "할리스"),
        "오렌지 핫주스" to listOf("파스쿠찌"),
        "루비자몽 핫주스" to listOf("파스쿠찌"),
        "루비자몽 주스" to listOf("파스쿠찌"),
        "복숭아 자두 스파클링" to listOf("할리스"),
        "청포도케일 주스" to listOf("이디야", "할리스"),
        "사과비트 주스" to listOf("할리스"),
        "홍시주스" to listOf("이디야"),
        "골드키위주스" to listOf("이디야"),
        "자몽네이블오렌지" to listOf("이디야"),
        "유자피나콜라다" to listOf("이디야"),
        "석류애플라임" to listOf("이디야"),
        "사과&당근 주스" to listOf("이디야"),
        "감귤&한라봉주스" to listOf("이디야"),
        "콤부차 복숭아망고" to listOf("이디야"),
        "콤부차 청포도레몬" to listOf("이디야"),

        )
    private val buttonList = mutableListOf<AppCompatButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_menu)

        editText = findViewById(R.id.search) // EditText ID에 맞게 변경
        parentLayout = findViewById(R.id.menu_list) // 버튼을 추가할 레이아웃 ID에 맞게 변경

        for (title in buttonTitles) {
            val button = AppCompatButton(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = title
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
            button.setBackgroundResource(R.drawable.button_round)
            button.setPadding(dpToPx(20), 0, 0, 0) // 왼쪽 패딩 설정
            button.visibility = View.GONE // 기본적으로는 숨겨둡니다.
            button.typeface = Typeface.create("sans", Typeface.NORMAL)
            buttonList.add(button)
            parentLayout.addView(button)

            button.setOnClickListener { view ->
                val coffeeName = (view as AppCompatButton).text.toString()
                val shops = coffeeShopMap[coffeeName]

                if (shops != null) {
                    val shopsText = shops.joinToString(", ") // 가게 정보 리스트를 쉼표로 연결된 문자열로 변환합니다.
                    showToast(shopsText)
                } else {
                    showToast("가게 정보가 없습니다.")
                }
            }

            var btnMenu : ImageButton = findViewById(R.id.nav_menu)
            btnMenu.setOnClickListener {
                finish()
            }

            // 기존에 있던 맵 버튼 관련 코드 (변경하지 않음)
            val btnMap: ImageButton = findViewById(R.id.nav_map)
            btnMap.setOnClickListener {
                // 맵 버튼 클릭 시 MapActivity로 화면 전환
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 전
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력 중
                val inputText = charSequence.toString().toLowerCase()

                // 버튼 리스트를 순회하며 입력된 텍스트와 각 버튼의 텍스트를 비교하여 가시성 설정
                buttonList.forEach { button ->
                    if (button.text.toString().toLowerCase().contains(inputText)) {
                        button.visibility = View.VISIBLE // 입력과 일치하면 보이기
                    } else {
                        button.visibility = View.GONE // 입력과 일치하지 않으면 숨기기
                    }
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                // 입력 후
            }
        })
    }

    private fun dpToPx(dp: Int): Int {
        val density: Float = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}