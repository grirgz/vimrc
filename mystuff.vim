command! Scremlines %s/\n\n//

" was working on first SC doc
command! -range Scheader '<,'>!scformat.py header
command! -range Scdescription '<,'>!scformat.py description
command! -range Scmethod '<,'>!scformat.py method

let mapleader = "_"
map <Leader>h :Scheader<Enter>
map <Leader>d :Scdescription<Enter>
map <Leader>m :Scmethod<Enter>

set foldmethod=indent
highlight Folded ctermbg=none ctermfg=grey


map <F2> mW'X<F5>'W


