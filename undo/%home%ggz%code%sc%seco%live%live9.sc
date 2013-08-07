Vim�UnDo� ���k��&�#E'�<�[.��&���GV0{�   �                                   Q���    _�                      S        ����                                                                                                                                                                                                                                                                                                                            S   
       �           V   
    Q���    �   R   S       6   CSynthDef(\audiotrack, { arg out = 0, amp=0.20, bufnum = 0, sustain;           var playbuf, ou;   I        playbuf = PlayBuf.ar(2,bufnum,startPos:44100*0.046,doneAction:0);   A        //playbuf = PlayBuf.ar(2,bufnum,startPos:0,doneAction:0);   G		//ou = playbuf * EnvGen.ar(Env.asr(0.01,1,0.01), gate, doneAction:2);   I		ou = playbuf * EnvGen.ar(Env.linen(0.001,sustain,0.001), doneAction:2);           Out.ar(out, ou * amp);   	}).store;       lSynthDef(\audiotrack_noisegate, { arg out = 0, amp=0.20, bufnum = 0, sustain, noisegate=0.0, at=0.2, rt=0.2;           var playbuf, ou;   I        playbuf = PlayBuf.ar(2,bufnum,startPos:44100*0.046,doneAction:0);   A        //playbuf = PlayBuf.ar(2,bufnum,startPos:0,doneAction:0);   G		//ou = playbuf * EnvGen.ar(Env.asr(0.01,1,0.01), gate, doneAction:2);   		ou = playbuf;   N		//ou = ((ou < noisegate) * ou) + ((ou > noisegate) * (ou*100).tanh.distort);   		//ou = (ou*100).distort;   D		ou = ou * EnvGen.ar(Env.linen(0.001,sustain,0.001), doneAction:2);   1		ou = (Amplitude.kr(ou,at,rt) > noisegate) * ou;           Out.ar(out, ou * amp);   }, metadata:(specs:(   ,	noisegate: ControlSpec(0, 0.1, \lin, 0, 0),   (	at: ControlSpec(0, 1, \lin, 0.0001, 0),   '	rt: ControlSpec(0, 1, \lin, 0.0001, 0)   
))).store;       MSynthDef(\audiotrack_expander, { arg out = 0, amp=0.20, bufnum = 0, sustain,    ,			delay=0.046, fadein=0.001, fadeout=0.001,   N			wet=1, threshold=0.0, slopeBelow=1, slopeAbove=1, clampTime=0, relaxTime=0;           var playbuf, ou, cou;   I        playbuf = PlayBuf.ar(2,bufnum,startPos:44100*delay,doneAction:0);   		ou = playbuf;   G		ou = ou * EnvGen.ar(Env.linen(fadein,sustain,fadeout), doneAction:2);   V		cou = Compander.ar(ou, ou, threshold, slopeBelow, slopeAbove, clampTime, relaxTime);   "		ou = SelectX.ar(wet, [ou, cou]);           Out.ar(out, ou * amp);   }, metadata:(specs:(   (	delay: ControlSpec(0, 0.1, \lin, 0, 0),   *	fadein: ControlSpec(0, 0.01, \lin, 0, 0),   +	fadeout: ControlSpec(0, 0.01, \lin, 0, 0),   ,	threshold: ControlSpec(0, 0.1, \lin, 0, 0),   /	clampTime: ControlSpec(0, 1, \lin, 0.0001, 0),   .	relaxTime: ControlSpec(0, 1, \lin, 0.0001, 0)   
))).store;       ASynthDef(\metronome, { arg out=0, amp=1, gate=1, freq=220, pan=0;   	var ou;   2	ou = SinOsc.ar([freq,freq/2])+LFSaw.ar(freq+0.1);   	ou = LPF.ar(ou,freq,0.1);   B	ou = ou * EnvGen.ar(Env.asr(0.0001, 1, 0.01),gate, doneAction:2);   	ou = Pan2.ar(ou,pan,amp*3);   	Out.ar(out,ou);   	}).store;    5��