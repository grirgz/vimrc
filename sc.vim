
map <F2> mZ'W<F5>'Z
map <F3> mZ'X<F5>'Z

map <F4>b <Esc>:call SendToSC("s.boot;")<CR>
imap <F4>b <Esc>:call SendToSC("s.boot;")<CR>a

map <F4>k <Esc>:call SendToSC("Quarks.gui;")<CR>
imap <F4>k <Esc>:call SendToSC("Quarks.gui;")<CR>a

map <F4>g <Esc>:call SendToSC("if(GUI.current == SwingGUI) { GUI.qt } { GUI.swing };")<CR>
imap <F4>g <Esc>:call SendToSC("if(GUI.current == SwingGUI) { GUI.qt } { GUI.swing };")<CR>

map <F4>q <Esc>:call SendToSC("s.queryAllNodes;")<CR>
map <F4>t <Esc>:call SendToSC("s.plotTree;")<CR>

map <F4>l <Esc>:call SendToSC("s.meter;")<CR>
map <F4>f <Esc>:call SendToSC("FreqScope.new;")<CR>
map <F4>s <Esc>:call SendToSC("Stethoscope.new(s);")<CR>

map <F4>D <Esc>:call SendToSC("Debug.enableDebug = true;")<CR>
map <F4>d <Esc>:call SendToSC("Debug.enableDebug = false;")<CR>

map <F4>h <Esc>:call SendToSC("Help.gui;")<CR>

map <F4>j <Esc>:call Jack_connect()<CR>

map <F4>n <Esc>:call SendToSC("[~name, ~namex, ~index].debug(\"== NOM: name, namex, index\");")<CR>
map <F4>v <Esc>:call SendToSC("s.volume.gui;")<CR>
map <F4>m <Esc>:call SendToSC("~mixer_gui.new(Veco.main);")<CR>

map <F8> <Esc>:call SendToSC("thisProcess.stop;")<CR>

"map g<f6> <Esc>"nyiw :exe "call SendToSC(\"Pdef(\\\\" . getreg("n") . ").stop;\")"<CR>
map <F9><F5> <Esc>:call SendToSC("Pdef(~name).play;")<CR>
"map g<F5> <Esc>:call SendToSC("Pdef(~name).play;")<CR>
map <F9><F6> <Esc>:call SendToSC("Pdef(~name).stop;")<CR>
map <F11><F5> <Esc>:%call SClang_send()<CR>


""source ~/.vim/indent/sc_indent.vim
set dictionary=~/.scvim/sc_object_completion
set complete+=k
set sessionoptions-=options

function! Jack_connect( )
	call SendToSC("~initialize_jack.value;") 
endfunction

function! DeleteHiddenBuffers()
    let tpbl=[]
    call map(range(1, tabpagenr('$')), 'extend(tpbl, tabpagebuflist(v:val))')
    for buf in filter(range(1, bufnr('$')), 'bufexists(v:val) && index(tpbl, v:val)==-1')
        silent execute 'bwipeout' buf
    endfor
endfunction


function! RemoveNode()
	exe "silent !mv % /tmp/"
	bdelete
	tabedit .
endfunction

" ================ VECO
"
map <F11> :call SendToSC("~veco.show_gui;")<CR>

let g:SCveco_project_root = "~/code/sc/seco/vlive/"
let g:SCveco_project_path = g:SCveco_project_root . "v1"

function! SCveco_open_project( path )
	let g:SCveco_project_path = g:SCveco_project_root . a:path
	exe "silent !mkdir ". g:SCveco_project_path
	redraw!
	exe "cd " . g:SCveco_project_path
	call SendToSC( "Veco.force_init; ~veco.open_project(\"" . g:SCveco_project_path . "\");")
	source ~/.vim/sctile.vim
	set hidden
endfunction

command! -nargs=1 VecoOpenProject :call SCveco_open_project(<f-args>)

"function! SCveco_open_subbuffer( idx )
"	exe "drop %:r:r:r:r." . a:idx . ".scd"
"endfunction

function! SCveco_open_subbuffer( idx )
	let l:cc = "~veco.get_node_by_uname(~name).get_clip_by_index(" . a:idx . " - 1 ).define_variables;"
	call SendToSC( l:cc )
	exe "drop %:r:r:r:r." . a:idx . ".scd"
endfunction

function! SCveco_open_current_node_buffer()
	exe "drop %:r:r:r:r.scd"
	" read first line to get name and send it to SC
	normal mQgg
	call SClang_send()
	normal 'Q
endfunction

function! SCveco_open_buffer( key, idx )
	" echo "plop1"
	let l:cc = "~veco.open_buffer(" . a:idx . ", ~name);"
	" echo "plop2"
	let l:cc2 = "~veco.set_buffer_name(" . a:idx . ", ~name);"
	" echo "plop3"
	let l:cmd = "drop " . g:SCveco_project_path . "/" . a:key . ".scd"
	" echo "plop4"
	" let SC initialize the buffer and switch to it
	call SendToSC( l:cc )
	""echo "plop5"
	exe l:cmd
	""echo "plop6"
	" read first line to get name and send it to SC
	normal mQgg
	""echo "plop7"
	call SClang_send()
	""echo "plop8"
	normal 'Q
	""echo "plop9"
	call SendToSC( l:cc2 )
	""echo "plop0"
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
