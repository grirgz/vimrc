setlocal foldmethod=expr
setlocal foldexpr=GetPotionFold(v:lnum)

function! GetPotionFold(lnum)
	if getline(v:lnum) =~ "^//"
		return '0'
	else
		return '1'
	endif
endfunction
