Vim�UnDo� 0��n��A���K��Y}�m��F�k��px�   p                                   Qѭ�    _�                             ����                                                                                                                                                                                                                                                                                                                                                             Qѭ�     �               5�_�                            ����                                                                                                                                                                                                                                                                                                                                                             Qѭ�     �                  5�_�                            ����                                                                                                                                                                                                                                                                                                                                                             Qѭ�     �                 MIDIClient.initialized5�_�                            ����                                                                                                                                                                                                                                                                                                                                                             Qѭ�     �                 // embedInStream dans un Prout5�_�                            ����                                                                                                                                                                                                                                                                                                                                                             Qѭ�     �                (//"/home/ggz/code/sc/abcparser.sc".load;5�_�                            ����                                                                                                                                                                                                                                                                                                                                                             Qѭ�     �                .//"/home/ggz/code/sc/seco/classinstr.sc".load;5�_�                            ����                                                                                                                                                                                                                                                                                                                                                             Qѭ�     �         �      ~seq.init_midi;5�_�      	              ~        ����                                                                                                                                                                                                                                                                                                                            �           ~           V        Qѭ�     �   }   ~              //Mdef.samplekit(\castor, [   )//	"~/Musique/beast.wav".standardizePath,   +//	"~/Musique/blabla2.wav".standardizePath,   //]);5�_�      
           	   w        ����                                                                                                                                                                                                                                                                                                                            ~           ~           V        Qѭ�     �   v   w          //Debug.enableDebug = false;5�_�   	              
   q        ����                                                                                                                                                                                                                                                                                                                            u           q           V        Qѭ�     �   p   q                      D~tf = Pfunc({ arg ev; if(ev[\stepline] == 1) { \note } { \rest } });   A~ff = Pfunc({ arg ev; if(ev[\stepline1] == 1) { 1 } { \rest } });5�_�   
                 i        ����                                                                                                                                                                                                                                                                                                                            n           i           V        Qѭ�     �   h   i          .//~seq.append_samplelib_from_path("sounds/" );   <//~seq.append_samplelib_from_path("sounds/hydrogen/GMkit" );   C//~seq.append_samplelib_from_path("sounds/hydrogen/HardElectro1" );       ^//Mdef.samplekit(\deskkick, 20.collect{arg i; "/home/ggz/Musique/recording" +/+ i ++ ".wav"});   +//Mdef.main.model.bus_mode_enabled = false;5�_�                     a        ����                                                                                                                                                                                                                                                                                                                            g           a           V        Qѭ�    �   `   a              //~samplelib = [   //	"sounds/perc1.wav",   //	"sounds/pok1.wav",   //	"sounds/amen-break.wav",   //	"sounds/default.wav"   //];5��