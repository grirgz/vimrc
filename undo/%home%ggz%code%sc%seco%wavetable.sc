Vim�UnDo� �����M5�a<P��8�0EV`��]*s����  �   a			"~/Musique/archwavetable/Architecture Waveforms 2010 Wav24/Architecture Waveforms 2010 Wav24",  �         B       B   B   B    Q��   	 _�                             ����                                                                                                                                                                                                                                                                                                                                                             P�L    �       T      5�_�                   �        ����                                                                                                                                                                                                                                                                                                                           m          �           V        P��    �  �  �       �   &//~class_load_wavetable_dialog_old = (   //	apply_done: false,   6//	new: { arg self, apply_action, cancel_action, path;   V//		var wtpath, folderlist, filelist, filepath, selectedlist = List.new, selectedfile;   //		self = self.deepCopy;   //   (//		self.buffer = Buffer.alloc(s, 4096);   //   �//		wtpath = path ?? "~/Musique/archwavetable/Architecture Waveforms 2010 Wav24/Architecture Waveforms 2010 Wav24".standardizePath;   (//		self.signal = Signal.newClear(2048);   .//		folderlist = PathName.new(wtpath).folders;   //   E//		self.window = Window.new("choose wavetable", Rect(0,0,1300,400));   //		self.window.onClose = {   //			self.buffer.free;   //			self.synthnode.release;   //			if(self.apply_done.not) {   //				cancel_action.();   //			}   //		};   C//		self.layout = HLayoutView.new(self.window, Rect(0,0,1300,400));   //   G//		self.folderlistview = ListView.new(self.layout, Rect(0,0,300,400));   T//		self.folderlistview.items = folderlist.collect{ arg folder; folder.folderName };   2//		self.folderlistview.action = { arg view, b, c;   //			[a, b, c].debug("action");   -//			filelist = folderlist[view.value].files;   L//			self.filelistview.items = filelist.collect { arg file; file.fileName };   !//			self.filelistview.value = 0;   7//			self.filelistview.action.value(self.filelistview);   //		};   //   E//		self.filelistview = ListView.new(self.layout, Rect(0,0,300,400));   ///		//self.filelistview.items = ["bla", "rah"];   0//		self.filelistview.action = { arg view, b, c;   +//			[view, b, c].debug("filelist action");   )//			selectedfile = filelist[view.value];   %//			self.display_file(selectedfile);   //		};   //   H//		self.right_layout = VLayoutView.new(self.layout, Rect(0,0,300,400));   //   O//		self.buttons_layout = HLayoutView.new(self.right_layout, Rect(0,0,300,20));   //   E//		self.but_play = Button.new(self.buttons_layout, Rect(0,0,50,20));   "//		self.but_play.string = "Play";   //		self.but_play.action = {   //			var path;   )//			path = self.displayed_file.fullPath;   //			path.debug("play: path");   #//			self.synthnode.debug("synth");   .//			if(self.synthnode.isNil.debug("isNil")) {   //				"ou".debug;   ;//				~load_sample_in_wavetable_buffer.(self.buffer, path);   V//				self.synthnode = { Osc.ar(self.buffer, MouseX.kr(20, 380), mul:0.1) ! 2  }.play;   $//				self.but_play.string = "Stop";   //			} {   //				"iou".debug;   //				self.synthnode.release;   //				self.synthnode = nil;   $//				self.but_play.string = "Play";   //			}   //		};   //   D//		self.but_add = Button.new(self.buttons_layout, Rect(0,0,50,20));   "//		self.but_add.string = "+ Add";   //		self.but_add.action = {   $//			selectedlist.add(selectedfile);   D//			self.selectedlistview.items = selectedlist.collect(_.fileName);   //		};   //   D//		self.but_add = Button.new(self.buttons_layout, Rect(0,0,50,20));   "//		self.but_add.string = "- Rem";   //		self.but_add.action = {   8//			selectedlist.removeAt(self.selectedlistview.value);   D//			self.selectedlistview.items = selectedlist.collect(_.fileName);   //		};   //   B//		StaticText.new(self.buttons_layout, Rect(0,0,10,20)); //spacer   //   I//		self.but_movedown = Button.new(self.buttons_layout, Rect(0,0,50,20));   &//		self.but_movedown.string = "Down";    //		self.but_movedown.action = {   //			var pos, newpos, item;   '//			pos = self.selectedlistview.value;   '//			item = selectedlist.removeAt(pos);   B//			selectedlist.insert((pos+1).clip(0,selectedlist.size), item);   G//			self.selectedlistview.value = (pos+1).clip(0,selectedlist.size-1);   D//			self.selectedlistview.items = selectedlist.collect(_.fileName);   //		};   //   G//		self.but_moveup = Button.new(self.buttons_layout, Rect(0,0,50,20));   "//		self.but_moveup.string = "Up";   //		self.but_moveup.action = {   //			var pos, newpos, item;   '//			pos = self.selectedlistview.value;   '//			item = selectedlist.removeAt(pos);   B//			selectedlist.insert((pos-1).clip(0,selectedlist.size), item);   G//			self.selectedlistview.value = (pos-1).clip(0,selectedlist.size-1);   D//			self.selectedlistview.items = selectedlist.collect(_.fileName);   //		};   //   O//		self.selectedlistview = ListView.new(self.right_layout, Rect(0,0,300,150));   C//		self.selectedlistview.items = selectedlist.collect(_.fileName);   4//		self.selectedlistview.action = { arg view, b, c;   //			[a, b, c].debug("action");   B//			self.display_file(selectedlist[self.selectedlistview.value]);   //		};   //		   >//		self.plotter = Plotter("plot", parent: self.right_layout);   //   D//		self.but_apply = Button.new(self.right_layout, Rect(0,0,80,20));   $//		self.but_apply.string = "Apply";   //		self.but_apply.action = {   !//			apply_action.(selectedlist);   //			self.apply_done = true;   //			self.window.close;   //		};   //   I//		//self.pack_layout = VLayoutView.new(self.layout, Rect(0,0,300,400));   //   //   J//		//self.pack_title = TextField.new(self.pack_layout, Rect(0,0,300,20));   //   L//		//self.packlistview = ListView.new(self.pack_layout, Rect(0,0,300,360));   A//		//self.packlistview.items = selectedlist.collect(_.fileName);   2//		//self.packlistview.action = { arg view, b, c;   !//		//	[a, b, c].debug("action");   D//		//	self.display_file(selectedlist[self.selectedlistview.value]);   //		//};   //   //   "//		self.folderlistview.value = 0;   ://		self.folderlistview.action.value(self.folderlistview);   //   //		self.window.front;   //   	//		self;   //	},   //   "//	display_file: { arg self, file;   //		var sf, sig;   //		self.displayed_file = file;   +//		sf = SoundFile.openRead(file.fullPath);   //		sf.readData(self.signal);   //		sf.close;   ///		self.plotter.value = self.signal.as(Array);   //		self.window.refresh;   //		if(self.synthnode.notNil) {   A//			~load_signal_in_wavetable_buffer.(self.buffer, self.signal);   //		};   //	}   //   //);   //5�_�                    S        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�\     �   S   V  �       �   S   U  �    5�_�                    U       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�e     �   U   W  �       �   U   W  �    �   U   W          )�   T   W  �      ~class_wavetable_manager = ()5�_�                    W        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�g     �   V   X  �      		)5�_�                    W       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�g     �   V   X  �      	)5�_�                    W       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�h     �   V   X  �      )5�_�      	              U       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�l     �   T   V  �      ~class_wavetable_manager = (5�_�      
           	   U        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�n     �   T   V  �      ~class_${1} = (�   Z   \  �      );�   T   \  �      ~class5�_�   	              
   \        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�s     �   [   \           5�_�   
                 \        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�t     �   [   \          );5�_�                    Z       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�x     �   Z   ]  �      	�   Z   \  �    5�_�                    \       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�}     �   [   ]  �      	get_wavetable: { arg self${1};�   ]   _  �      	},�   \   ^  �      		${2}�   [   _  �      	get_wavetable()5�_�                    \       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�!     �   \   ^  �    5�_�                    \        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�#     �   \   ^  �      	�   \   ^  �    5�_�                    ]       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�(     �   \   ^  �      		self.wt_dict[]5�_�                    ^        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�G     �   ]   ^          	5�_�                    ^        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�G     �   ]   ^           5�_�                    ^       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�G     �   ^   a  �      	�   ^   `  �    5�_�                    `       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�L     �   _   a  �      	get_names: {}5�_�                    `       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�L     �   _   c  �      	get_names: {  }5�_�                    b       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�O     �   a   c  �      	}5�_�                    `       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�P     �   `   b  �      	�   `   b  �    5�_�                    b        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�V    �   a   b          	5�_�                    W       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P�\     �   W   Z  �      		�   W   Y  �    5�_�                    S       ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �   S   �  �    5�_�                    S        ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �   S   U  �    5�_�                    U   
    ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �   T   V  �      ~curvebank = (5�_�                    �   
    ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �   �   �  �      G~curvebank.known = false; // FIXME: should be "know", is it required ? 5�_�                          ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �       �      		self.wt_dict =5�_�                           ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �      		self.wt_dict.keys5�_�      !                     ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �      		self.wt_dict[]keys5�_�       "           !         ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �      		self.wt_dict[\get_keys]keys5�_�   !   #           "         ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �       		self.wt_dict[\get_keys].()keys5�_�   "   $           #     (    ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �      ,		self.wt_dict[\get_keys].(self.wt_dict)keys5�_�   #   %           $     (    ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �      +		self.wt_dict[\get_keys].(self.wt_dict)eys5�_�   $   &           %     (    ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �      *		self.wt_dict[\get_keys].(self.wt_dict)ys5�_�   %   '           &     (    ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��     �  
    �      )		self.wt_dict[\get_keys].(self.wt_dict)s5�_�   &   (           '     (    ����                                                                                                                                                                                                                                                                                                                           �          �           V        P��    �  
    �      (		self.wt_dict[\get_keys].(self.wt_dict)5�_�   '   )           (  �       ����                                                                                                                                                                                                                                                                                                                                                             P�E�    �  �  �  �      *		paths = ~passive_config.wavetable_paths;5�_�   (   *           )  �       ����                                                                                                                                                                                                                                                                                                                                                             P�E�     �  �  �  �    5�_�   )   +           *  �        ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      	wavetable_paths: [   N		// put here path to directory containing wavetable files (simple short .wav)   `		"~/Musique/archwavetable/Architecture Waveforms 2010 Wav24/Architecture Waveforms 2010 Wav24",   	],5�_�   *   ,           +  �       ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		wavetable_paths: [5�_�   +   -           ,  �   	    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�    �  �  �  �      		�  �  �  �    5�_�   ,   .           -  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = wavetable_paths: [5�_�   -   /           .  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = avetable_paths: [5�_�   .   0           /  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = vetable_paths: [5�_�   /   1           0  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = etable_paths: [5�_�   0   2           1  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = table_paths: [5�_�   1   3           2  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = able_paths: [5�_�   2   4           3  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = ble_paths: [5�_�   3   5           4  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = le_paths: [5�_�   4   6           5  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = e_paths: [5�_�   5   7           6  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = _paths: [5�_�   6   8           7  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = paths: [5�_�   7   9           8  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = aths: [5�_�   8   :           9  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = ths: [5�_�   9   ;           :  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = hs: [5�_�   :   <           ;  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = s: [5�_�   ;   =           <  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths = : [5�_�   <   >           =  �   
    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�     �  �  �  �      		paths =  [5�_�   =   ?           >  �       ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�    �  �  �  �      		],5�_�   >   @           ?  �   /    ����                                                                                                                                                                                                                                                                                                                           �         �          V       P�E�    �  �  �  �      1			~class_wavetable_sigfunc_file.new(~curvebank),5�_�   ?   A           @  �       ����                                                                                                                                                                                                                                                                                                                                                             Q��     �  �  �  �    5�_�   @   B           A  �       ����                                                                                                                                                                                                                                                                                                                                                             Q��     �  �  �  �      a			"~/Musique/archwavetable/Architecture Waveforms 2010 Wav24/Architecture Waveforms 2010 Wav24",5�_�   A               B  �       ����                                                                                                                                                                                                                                                                                                                                                             Q��   	 �  �  �  �      			"~/Musique/",5��