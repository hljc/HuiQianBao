package com.ceq.app_core.interfaces;


import com.ceq.app_core.annotation.Annotation_Language;

public interface Inter_Global_Language {
	@Annotation_Language("中文-简(zn)")
	int LANGUAGE_ZHONG_WEN_JIAN = 0;

	@Annotation_Language("中文-繁(cht)")
	int LANGUAGE_ZHONG_WEN_FAN = 1;
	@Annotation_Language("粤语(cht)")
	int LANGUAGE_YUE_YU = 2;

	@Annotation_Language("英语(en)")
	int LANGUAGE_YING_YU = 3;

	@Annotation_Language("日语(jp)")
	int LANGUAGE_RI_YU = 4;

	@Annotation_Language("韩语(kor)")
	int LANGUAGE_HAN_YU = 5;

	/*
	 * @Annotation_Language("泰语(th)") int LANGUAGE_TAI_YU = 6;
	 * 
	 * @Annotation_Language("法语(fra)") int LANGUAGE_FA_YU = 7;
	 * 
	 * @Annotation_Language("西班牙语(spa)") int LANGUAGE_XIBANYA_YU = 8;
	 * 
	 * @Annotation_Language("阿拉伯语(ara)") int LANGUAGE_ALABAO_YU = 9;
	 * 
	 * @Annotation_Language("俄语(ru)") int LANGUAGE_E_YU = 10;
	 * 
	 * @Annotation_Language("葡萄牙语(pt)") int LANGUAGE_PUTAOYA_YU = 11;
	 * 
	 * @Annotation_Language("德语(de)") int LANGUAGE_DE_YU = 12;
	 * 
	 * @Annotation_Language("意大利语(it)") int LANGUAGE_YIDALI_YU = 13;
	 */
}
