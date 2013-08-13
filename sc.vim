
map <F2> mZ'W<F5>'Z
map <F3> mZ'X<F5>'Z

map <F4>b :call SendToSC("s.boot;")<CR>
imap <F4>b <Esc>:call SendToSC("s.boot;")<CR>a

map <F4>k :call SendToSC("Quarks.gui;")<CR>
imap <F4>k <Esc>:call SendToSC("Quarks.gui;")<CR>a

map <F4>g :call SendToSC("if(GUI.current == SwingGUI) { GUI.qt } { GUI.swing };")<CR>
imap <F4>g :call SendToSC("if(GUI.current == SwingGUI) { GUI.qt } { GUI.swing };")<CR>

map <F4>q :call SendToSC("s.queryAllNodes;")<CR>
map <F4>t :call SendToSC("s.plotTree;")<CR>

map <F4>m :call SendToSC("s.meter;")<CR>
map <F4>f :call SendToSC("FreqScope.new;")<CR>
map <F4>s :call SendToSC("Stethoscope.new(s);")<CR>

map <F4>D :call SendToSC("Debug.enableDebug = true;")<CR>
map <F4>d :call SendToSC("Debug.enableDebug = false;")<CR>

map <F4>h :call SendToSC("Help.gui;")<CR>

map <F4>j :call Jack_connect()<CR>

map <F8> :call SendToSC("thisProcess.stop;")<CR>

map g<f6> <Esc>"nyiw :exe "call SendToSC(\"Pdef(\\\\" . getreg("n") . ").stop;\")"<CR>


""source ~/.vim/indent/sc_indent.vim
set dictionary=~/.scvim/sc_object_completion
set complete+=k
set sessionoptions-=options

function! Jack_connect( )
	call SendToSC("if(MIDIClient.initialized == false) { MIDIClient.init; };") 
	normal !jack_connect A-PRO:midi/playback_2 SuperCollider:midi/capture_1<CR>:redraw!<CR>
endfunction

" ================ VECO
"
map <F11> :call SendToSC("~veco.show_gui;")<CR>

let g:SCveco_project_root = "~/code/sc/seco/vlive/"
let g:SCveco_project_path = g:SCveco_project_root . "v1"

function! SCveco_open_project( path )
	let g:SCveco_project_path = g:SCveco_project_root . a:path
	exe "cd " . g:SCveco_project_path
	call SendToSC( "~veco.open_project(\"" . g:SCveco_project_path . "\");")
	source ~/.vim/sctile.vim
	set hidden
endfunction

command! -nargs=1 VecoOpenProject :call SCveco_open_project(<f-args>)

function! SCveco_open_buffer( key, idx )
	let l:cc = "~veco.open_buffer(" . a:idx . ", ~name);"
	let l:cc2 = "~veco.set_buffer_name(" . a:idx . ", ~name);"
	let l:cmd = "drop " . g:SCveco_project_path . "/" . a:key . ".scd"
	" let SC initialize the buffer and switch to it
	call SendToSC( l:cc )
	exe l:cmd
	" read first line to get name and send it to SC
	normal mQgg
	call SClang_send()
	normal 'Q
	call SendToSC( l:cc2 )
endfunction

function! SCveco_copy_buffer( key, idx )
	let l:cc2 = "~veco.copy_buffer(" . a:idx . ", ~name);"
	call SendToSC( l:cc2 )
endfunction

" ================

function! s:get_visual_selection()
  let [lnum1, col1] = getpos("'<")[1:2]
  let [lnum2, col2] = getpos("'>")[1:2]
  let lines = getline(lnum1, lnum2)
  let lines[-1] = lines[-1][: col2 - 1]
  let lines[0] = lines[0][col1 - 1:]
  return join(lines, "\n")
endfunction

function! SCreplaceCmd( cmd )
	let cc = "~replace.(". cmd . ");";
	call SendToSC(cc);
endfunction

function! SCreplace() range
	let cc = "~replace.(\"". s:get_visual_selection() . "\");"
	call SendToSC(cc)
	sleep 50m
	let l = readfile( '/tmp/mysc')
	exe "normal gvc". join(l,"\n") . "\e" 
	"exe "normal i" . join(l,"\n") . "\n" . "\e"
endfunction

function! SCreplace2() range
	"let cc = "~replace.(". s:get_visual_selection() . ");"
	let cc = s:get_visual_selection() . ""
	call SendToSC(cc);
	"let cc = "~replace.();"
	"let cc = s:get_visual_selection()
	"exe "visual d"
	"exe "'<,'>c". cc . "\e"
	exe "'<,'>d"
	exe "normal i" . cc . "\e"
	"call SendToSC(cc);
endfunction

function! s:get_visual_selection()
  let [lnum1, col1] = getpos("'<")[1:2]
  let [lnum2, col2] = getpos("'>")[1:2]
  let lines = getline(lnum1, lnum2)
  let lines[-1] = lines[-1][: col2 - 1]
  let lines[0] = lines[0][col1 - 1:]
  return join(lines, "\n")
endfunction

function! SCinterpretInPlace() range
	let filename = "/tmp/scvim_interpret_in_place"
	if filewritable(filename) == 0
		exe "!mkfifo " . filename
	endif
	let code = '{ arg fn, cmd; File.use(fn,"w", { arg file; file.write( cmd.interpret.asCompileString ) }) }.value("'
		\ . filename . '", "' . s:get_visual_selection() . '");'
	call SendToSC(code)
	let l = readfile( filename)
	exe "normal gvc". join(l,"\n") . "\e" 
endfunction

function! SCcallFunctionOnSelection() range
	let filename = "/tmp/scvim_interpret_in_place"
	if filewritable(filename) == 0
		exe "!mkfifo " . filename
	endif
	let code = '{ arg fn, cmd; File.use(fn,"w", { arg file; file.write( ~replace.( cmd ) ) }) }.value("'
		\ . filename . '", "' . s:get_visual_selection() . '");'
	call SendToSC(code)
	let l = readfile( filename)
	exe "normal gvc". join(l,"\n") . "\e" 
endfunction
