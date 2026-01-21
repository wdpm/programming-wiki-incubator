/**
 * 这里给出了一个案件中动议（Motion）的类型定义。以下是各个字段的含义：

- from: 动议发起方，可以是被告（defendant）或原告（plaintiff）
- reason: 动议的原因
- classification: 动议的分类，可以是“驳回”（dismiss）、“抑制”（suppress）、“地点”（venue）、“无罪”（acquittal）、
 “更正”（correction）或“新审”（new trial）
- step: 动议所处的阶段，可以是“庭前”（pre-trial）或“庭后”（post-trial）
- deliberationHours: 法官对动议进行审议的时间，以小时为单位
- annoyedJustice: 法官是否因动议而感到烦恼
- estimatedDeliberationHours: 预计法官对动议进行审议的时间，以小时为单位
- status: 动议的状态，可以是“允许”（allowed）、“拒绝”（denied）或“待定”（pending）
 */

export type MotionBase = {
	from: "defendant" | "plaintiff";
	reason: string;
};

export type PreTrialMotion = MotionBase & {
	classification: "dismiss" | "suppress" | "venue";
	step: "pre-trial";
};

export type PostTrialMotion = MotionBase & {
	classification: "acquittal" | "correction" | "new trial";
	step: "post-trial";
};

export type TrialMotion = PostTrialMotion | PreTrialMotion;

export type AllowedMotion = TrialMotion & {
	deliberationHours: number;
	status: "allowed";
};

export type DeniedMotion = TrialMotion & {
	annoyedJustice: boolean;
	deliberationHours: number;
	status: "denied";
};

export type PendingMotion = TrialMotion & {
	estimatedDeliberationHours: number;
	status: "pending";
};

export type Motion = AllowedMotion | DeniedMotion | PendingMotion;

export const motions: Motion[] = [
	{
		annoyedJustice: true,
		classification: "acquittal",
		deliberationHours: 1,
		from: "defendant",
		reason:
			"I left all my money to Bruce Mathis, the real father of my children.",
		status: "denied",
		step: "post-trial",
	},
	{
		annoyedJustice: true,
		classification: "correction",
		deliberationHours: 2.5,
		from: "plaintiff",
		reason: "The tenant has ninety days to vacate.",
		status: "denied",
		step: "post-trial",
	},
	{
		classification: "suppress",
		deliberationHours: 4,
		from: "plaintiff",
		reason: "Frank was never allowed in the house.",
		status: "allowed",
		step: "pre-trial",
	},
	{
		classification: "new trial",
		estimatedDeliberationHours: 3,
		from: "defendant",
		reason: "The duel's been accepted. There's no backing out. That's the law.",
		status: "pending",
		step: "post-trial",
	},
	{
		annoyedJustice: false,
		classification: "dismiss",
		deliberationHours: 0.5,
		from: "plaintiff",
		reason:
			"It seems like you have a tenuous grasp on the English language in general.",
		status: "denied",
		step: "pre-trial",
	},
	{
		annoyedJustice: true,
		classification: "correction",
		deliberationHours: 1.5,
		from: "defendant",
		reason: "Fillibuster?",
		status: "denied",
		step: "post-trial",
	},
	{
		annoyedJustice: true,
		classification: "venue",
		deliberationHours: 0.25,
		from: "defendant",
		reason: "A time was never specified for the duel.",
		status: "denied",
		step: "pre-trial",
	},
	{
		annoyedJustice: true,
		classification: "correction",
		deliberationHours: 5,
		from: "plaintiff",
		reason: "He's making a few good points!",
		status: "denied",
		step: "post-trial",
	},
];
