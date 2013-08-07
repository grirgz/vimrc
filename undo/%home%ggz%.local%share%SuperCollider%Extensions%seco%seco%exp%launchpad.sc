Vim�UnDo� �ɦ�L>�5�q��/ܞL%����1�#>�zx                                    R*E    _�                             ����                                                                                                                                                                                                                                                                                                                                                             R*D    �                   5�_�                             ����                                                                                                                                                                                                                                                                                                                                                             R*+     �             7   
Launchpad{       	classvar <>midiout;       	classvar <>pads;//2DARRAY       2	classvar nonr,nofr,cor;//MIDI RESPONDER FUNCTIONS       	*initClass{        		if(Launchpad.initMIDI.notNil,{       			Launchpad.setupLP;       (			Launchpad.pads=Array2D.fromArray(9,9,       8				Array.fill(81,{|i|LPPad.new((i/9).asInteger,i%9)}));       		})       	}       	*initMIDI{       ,		var non,nof,co,midisource,mididestination;       		MIDIClient.init;       H		midisource=MIDIClient.sources.detect{|a|a.name.contains("Launchpad")};       		if(midisource.notNil,{       S			mididestination=MIDIClient.destinations.detect{|a|a.name.contains("Launchpad")};       %			MIDIIn.connect(0, midisource.uid);       +			midiout=MIDIOut(0, mididestination.uid);       +			MIDIOut.connect(0, mididestination.uid);       			non={|src,chan,num,val|       				var x=num%16;       				var y=(num-x)/16;       $				//[\on,src,chan,num,val].postln;       				//r{       				if(val===127,{       					pads.at(x,y+1).responseOn;       					},{       !						pads.at(x,y+1).responseOff;       				});       				//}.play;       			};       >			nonr=NoteOnResponder(non,midisource.uid,swallowEvent:true);       			nof={|src,chan,num,val|       				var x=num%16;       				var y=(num-x)/16;       %				//[\off,src,chan,num,val].postln;       				//r{       				pads.at(x,y+1).responseOff;       				//}.play;       			};       ?			nofr=NoteOffResponder(non,midisource.uid,swallowEvent:true);       			co={|src,chan,num,val|       				var x=num-104;       $				//[\co,src,chan,num,val].postln;       				//r{       				if((val%2)===1,{       					pads.at(x,0).responseOn;       					},{       						pads.at(x,0).responseOff;       				})       				//}.play;       			};       8			cor=CCResponder(co,midisource.uid,swallowEvent:true);       			^ 1;       			},{       %				"Launchpad Not available".postln;       					^nil;       		})       	}       	*resetLED{       			//reset        		pads.do{|i|i.led=LPLED.black};       		midiout.control(0,0,0);       	}       		*xyMode{       		midiout.control(0,0,1);       	}       	*drumMode{       		midiout.control(0,0,2);       	}       	*setContrast{|num=1,denum=6|       1		midiout.control(0,30,((16*(num-1))+(denum-3)));       	}       
	*setupLP{       		this.resetLED;       		this.xyMode;       		this.setContrast;       	}       	*xyMIDINode{|x,y|       		^(x+(y*16))       	}       	*pos2CC{|pos|       		^(104+pos)       	}       	*getRange{|fx,fy,tx,ty|       		var ttx,tty;       %		//ttx=if(fx>=(tx?0),{fx+1},{tx+1});       &		//tty=if(fy>=(ty?0),{fy+1},{ty+1s});       		ttx=tx?fx;       		tty=ty?fy;       *		^LPRange(pads.copyRange(fx,fy,ttx,tty));       	}       	//simply set LEDs       	*setLED{|x,y,val|       		//\setLED.postln;       		if(y==0,{        			Launchpad.setTopLED(x,y,val);       		},{       #			Launchpad.setMatrixLED(x,y,val);       		})       	}       	*setMatrixLED{|x,y,val|       6		midiout.noteOn(0, Launchpad.xyMIDINode(x,y-1), val);       		}       	*setTopLED{|x,y,val|       /		midiout.control(0, Launchpad.pos2CC(x), val);       		}       	*setPlayLED{|x,y,val|       4		midiout.noteOn(0, Launchpad.xyMIDINode(8,y), val);       	}       	//set LEDs for initialisation       	*setAllLED{|ledarray|       		"todo".postln;       	}       	//doublebuffering?       	//flashing?       }               LPRange{       	var <>pads;//2DARRAY       		*new{|b|       		^super.new.init(b);       	}       		init{|b|       			pads=b;       	}       	getRange{|fx,fy,tx,ty|       		var ttx,tty;       		ttx=tx?fx;       		tty=ty?fy;       *		^LPRange(pads.copyRange(fx,fy,ttx,tty));       	}       	parent_{|o|       		pads.do{|i|i.parent=o};       	}       		led_{|l|       		pads.do{|i|i.led=l};       	}       }               LPPad{       	var <>x,<>y;       		var led;       	var <>pastparent;       	var pastled;       	var <parent;       		led_{|l|       		var v,nv;       		pastled=led;       		led=l;       =		v=if(pastled.notNil,{pastled.value.vel},{LPLED.black.vel});       		nv=led.value.vel;       8		//[\setled,x,y,v,nv,l.value.vel,led.value.vel].postln;       '		if(v!=nv,{Launchpad.setLED(x,y,nv)});       &		//Launchpad.setLED(x,y,l.value.vel);       	}       	parent_{|p|       		pastparent=parent;       		parent=p;        		//p.lppad=this;		why was this?       	}       	unparent{|blacking=true|       		parent=pastparent;       		pastparent=nil;       		//[\parent,parent].postln;       k		if(parent.isNil,{if(blacking,{led={LPLED.black};Launchpad.setLED(x,y,led.value.vel)})},{parent.initLED});       	}       	*new{|x,y|       		^super.new.init(x,y);       	}       	init{|ax,ay,p|       		x=ax;       		y=ay;       		parent=p;       	}       	responseOn{       		parent.responseOn(this);       	}       	responseOff{       		parent.responseOff(this);       	}       }                       LPLED{       	var <>green,<>red,clear,copy;       $	*new{|green=0,red=0,clear=1,copy=1|       (		^super.new.init(green,red,clear,copy);       	}       	*black{^this.new(0)}       %	*green{|s=3|s=s.min(3);^this.new(s)}       %	*red{|s=3|s=s.min(3);^this.new(0,s)}       (	*amber{|s=3|s=s.min(3); ^this.new(s,s)}       *	*yellow{|s=2|s=s.min(2);^this.new(s+1,s)}       *	*orange{|s=2|s=s.min(2);^this.new(s,s+1)}       	next{       
		var g,r;       4		var ns=4.collect{|g| 4.collect{|r|[g,r]}}.flatten;       4		#g,r=ns[(ns.indexOfEqual([green,red])+1)%ns.size];       
		green=g;       		red=r;       	}       	init{|gr,re,cl,co|       		green=gr;       			red=re;       		clear=cl;       
		copy=co;       	}       	vel{       		^(       )			(2.pow(4)*green.value.round.asInteger)       			+       			(2.pow(3)*clear)       			+       			(2.pow(2)*copy)       			+       			(red.value.round.asInteger)       		);       	}       }               $LPButton{//eine typische abstraktion       	var <>parent;        	var <>onFunction,<>offFunction;       	*new {|onFunction,offFunction|       -		^super.new.padinit(onFunction,offFunction);       	}       	padinit{| onf, offf|       		onFunction= onf;       		offFunction= offf;       	}       	responseOn{|pad|       		onFunction.value(pad);       	}       	responseOff{|pad|       		offFunction.value(pad);       	}       		initLED{       		parent.initLED;       	}       }               7LPWidget{//lpwidgets sind moegliche parents fuer ranges       	var <>range;       	var onfront;       	var onhide;       	var isfront;       	*new{|range,onfront,onhide|       )		^super.new.winit(range,onfront,onhide);       	}       	winit{|r,of,oh|       
		range=r;       		onfront=of;       		onhide=oh;       	}       
	front{|r|       		onfront.value(this);       		isfront=true;       		range=r?range;       		this.initParent;       		this.initLED;       	}       	hide{|blacking=true|       		onhide.value(blacking);       		isfront!?{       			isfront=nil;       *			range.pads.do{|p|p.unparent(blacking)};       		};       	}       	responseOn{|pad|       	}       	responseOff{|pad|       	}       	initParent{       		range.parent=this;       	}       		initLED{       		//range.led=...;       	}       }               LPBlackWidget : LPWidget{       	var <>led;       	*new {|range,onfront,onhide|       *		^super.new(range,onfront,onhide).bwinit;       	}       	bwinit{       		led=LPLED.black;       	}       		initLED{       		range.led=led;       	}       }               LPLEDButton : LPWidget{       	var <>button;       	var <>onled;       	var <>offled;       	var state;       1	*new {|button,onled,offled,range,onfront,onhide|       C		^super.new(range,onfront,onhide).ledpadinit(button,onled,offled);       	}       	ledpadinit{|bt,onl,offl,r|       
		state=0;       		button=bt??{LPButton()};       		button.parent=this;       		onled=onl??{LPLED.red(2)};        		offled=offl??{LPLED.green(2)};       	}       		initLED{       		range.led=offled;       	}       	responseOn{|pad|       		button.responseOn(pad);       		range.led=onled;       	}       	responseOff{|pad|       		button.responseOff(pad);       		range.led=offled;       	}       }               LPLEDSwitch : LPWidget{       	var functions;       
	var leds;       	var <state;       1	*new{|functions,leds,state,range,onfront,onhide|       G		^super.new(range,onfront,onhide).ledbuttoninit(functions,leds,state);       	}       	ledbuttoninit{|f,l,s|       		functions=f;       			leds=l;       
		state=s;       h		if(functions.size!=leds.size,{^MethodError("function array size unequal led array size",this).throw});       		state=functions.size-1;       	}       		initLED{       		range.led=leds[state];       	}       	responseOn{|pad|       &		this.state=(state+1)%functions.size;       		functions[state].value(pad);       	}       	state_{|x|       
		state=x;       		this.initLED;       	}       }               LPFader : LPWidget{       	var statevarroutine;       	var <>onoff;       
	var func;       	var <>state;       	var getstatefunc;       
	var size;       	var oncolor;       	var offcolor;       @	*new {|func,getstatefunc,oncolor,offcolor,range,onfront,onhide|       $		//"LPVFader is not tested".postln;       M		^super.new(range,onfront,onhide).finit(func,getstatefunc,oncolor,offcolor);       	}       	finit{|f,g,onc,ofc|       		getstatefunc=g;       			func=f;       		oncolor=onc;       		offcolor=ofc;       	}       	updateState{|padpos|       		onoff=true;       		statevarroutine.stop;       		statevarroutine=r{       			while({onoff},{       				func.value(this,padpos);       				this.updateLED;       				0.2.wait;       			})       			}.play;       	}       	initParent{       		size=range.pads.size;       !		state=getstatefunc.value(this);       		range.pads.do{|item,i|       =			item.parent=LPButton({this.updateState(i)},{onoff=false});       			item.parent.parent=this;       		};       	}       		initLED{       		this.updateLED;       	}       	updateLED{       		var ledstate,lls;       		ledstate=state*size;       		range.pads.do{|item,i|       			if(ledstate>((size-1)-i),{       				lls=ledstate-((size-1)-i);       				ledstate=ledstate-lls;       *				item.led=LPLED.perform(oncolor,lls*3);       $				//{item.led=LPLED.green(lls*3)},       !				//{item.led=LPLED.red(lls*3)}       				//);       			},{       '				item.led=LPLED.perform(offcolor,1);       			})       		}       	}       }               LPParameterFader :LPFader{       
	var rand;       <	*new{|parameter,oncolor,offcolor,range,onfront,onhide,rand|       [		^super.new(nil,nil,nil,nil,range,onfront,onhide).pfinit(parameter,oncolor,offcolor,rand);       	}       &	pfinit{|parameter,oncolor,offcolor,r|       			rand=r;       		this.finit({|fader,padpos|       �			var state=parameter.asSpec.map(fader.state+[0.1,0.02,0.005,rand.postln.value.postln.bilinrand.postln(\rand), -0.005, -0.02, -0.1][padpos]);       -			fader.state=parameter.asSpec.unmap(state);       Z			LPSetupNormalGlobals.synth.value.node.set(parameter,state.postln(" "++parameter++":"));       		},{       `			var value=LPSetupNormalGlobals.synth.value.node.getKeysValues.detect{|i|i[0]===parameter}[1];       !			parameter.asSpec.unmap(value);       		},oncolor,offcolor);       	}       }                       LPSpecFader : LPWidget{       	var statevarroutine;       	var onoff;       
	var func;       	var state;       
	var spec;       
	var size;       '	*new {|func,spec,range,onfront,onhide|       $		//"LPVFader is not tested".postln;       4		^super.new(range,onfront,onhide).finit(func,spec);       	}       	finit{|f,s|       			spec=s;       !		state=spec.unmap(spec.default);       			func=f;       	}       	updateState{|padpos|       %		var ud = ((size - 1) / 2) - padpos;       =		var specstep=if(spec.step==0,{spec.range/512},{spec.step});       		var step=specstep/spec.range;       		onoff=true;       		statevarroutine.stop;       		statevarroutine=r{       			while({onoff},{       				case       C				//{ud >  0}{state=spec.unmap(spec.map(state)+(step*2.pow(ud)))}       6				{ud >  0}{state=((state)+(step*2.pow(ud))).min(1)}       				{ud == 0}{state.postln;}       G				//{ud <  0}{state=spec.unmap(spec.map(state)-(step*2.pow(ud.abs)))}       :				{ud <  0}{state=((state)-(step*2.pow(ud.abs))).max(0)}       				;       				this.updateLED;       %				func.value(spec.map(state),this);       				(0.1/ud.abs).wait;       			})       			}.play;       	}       	initParent{       		size=range.pads.size;       		range.pads.do{|item,i|       =			item.parent=LPButton({this.updateState(i)},{onoff=false});       			item.parent.parent=this;       		};       	}       		initLED{       		this.updateLED;       	}       	updateLED{       		var ledstate,lls;       		var green=true;       		ledstate=state*size;       		range.pads.do{|item,i|       			if(ledstate>((size-1)-i),{       				lls=ledstate-((size-1)-i);       				ledstate=ledstate-lls;       				if(green,       #					{item.led=LPLED.green(lls*3)},        					{item.led=LPLED.red(lls*3)}       				);       			},{       				item.led=LPLED.black;       			})       		}       	}       }               LPSwitchArray : LPWidget{       	var <>functions;       	var <>onleds;       	var <>ofleds;       	var <state;       4	*new{|functions,onleds,ofleds,range,onfront,onhide|       C		^super.new(range,onfront,onhide).sainit(functions,onleds,ofleds);       	}       	sainit{|f,onl,ofl|       		//[f,onl,ofl].postln;       		functions=f;       		onleds=onl;       		ofleds=ofl;       i		if(functions.size!=onl.size,{^MethodError("function array size unequal onled array size",this).throw});       j		if(functions.size!=ofl.size,{^MethodError("function array size unequal offled array size",this).throw});       b		if(onl.size!=ofl.size,{^MethodError("onleds array size unequal offled array size",this).throw});       	}       	initParent{       		//size=range.pads.size;       		range.pads.do{|item,i|       /			item.parent=LPButton({this.updateState(i)});       			item.parent.parent=this;       		};       	}       		initLED{       		range.pads.do{|item,i|       			if(i==state,{       				item.led=onleds[i];       			},{       				item.led=ofleds[i];       			})       		}       	}       	executeState{       8		if(state.notNil,{functions[state].value(state,this)});       	}       	updateState{|i|       		this.state=i;       		this.executeState;       	}       	state_{|x|       5		range.pads.asArray.at(state?0).led=ofleds[state?0];       
		state=x;       1		range.pads.asArray.at(state).led=onleds[state];       	}       }5��