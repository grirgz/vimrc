    function! MoveCursor_example(forward)
        let stop_re = '[a-z]'
        let curpos=getcurpos()
        while search('\<', 'W'.a:forward)
            if synIDattr(synID(line("."), col("."), 1), "name") =~ stop_re
                break
            endif
        endwhile
        if synIDattr(synID(line("."), col("."), 1), "name") !~ stop_re
            call setpos('.', curpos)
        endif
    endfunction


function! MoveToWord(forward)
	call search('\<', 'W'.a:forward)
endfunction
function! VisualMoveToWord(forward)
	normal gv
	call MoveToWord(a:forward)
endfunction

nnoremap W :call MoveToWord('')<cr>
nnoremap B :call MoveToWord('b')<cr>
vnoremap W :<C-u>call VisualMoveToWord('')<cr>
vnoremap B :<C-u>call VisualMoveToWord('b')<cr>

"nnoremap W :call search('\<', 'W')<cr>
"nnoremap B :call search('\<', 'Wb')<cr>
"vnoremap W :<C-u>call VisualMoveToWord('')<cr>
"vnoremap B :<C-u>call VisualMoveToWord('b')<cr>
"vnoremap B :<C-u>call VisualMoveToWord('b')<cr>

"vnoremap W :<C-u>search('\<')<cr>

""	Pen.lineTo(Point(p.x, 0+offset)); // finish at y center to close the path nicely
""	Pen.lineTo(this.secondPointToPixelPoint(Point(0,0)));
