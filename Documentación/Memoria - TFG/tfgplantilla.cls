\ProvidesClass{tfgplantilla}[2017/04/13 v1 LaTeX document class]

% Opciones
\newif\iflisttables\listtablesfalse
\newif\iflistfigures\listfiguresfalse
\newif\iflistquadres\listquadresfalse
\newif\iflistalgorithms\listalgorithmsfalse

\DeclareOption{spanish}{\PassOptionsToPackage{english,main=spanish}{babel}}
\DeclareOption{english}{\PassOptionsToPackage{spanish,main=english}{babel}}

\DeclareOption{listoftables}{\listtablestrue}
\DeclareOption{listoffigures}{\listfigurestrue}
\DeclareOption{listofquadres}{\listquadrestrue}
\DeclareOption{listofalgorithms}{\listalgorithmstrue}

\DeclareOption*{\PassOptionsToClass{\CurrentOption}{book}}
\ProcessOptions

\LoadClass{book}

% Codificacio dels caracteres 
\RequirePackage[T1]{fontenc}

% Sistema babel 
\RequirePackage{babel}

% Tipografia base (palatino)
\RequirePackage{mathpazo}

% Geometria (a4, marges
\RequirePackage[a4paper,left=3cm,right=3cm,top=2.35cm,bottom=2.35cm]{geometry}

% Escritura de los enlaces de internet; ejemplo: \url{www.upv.es}
\RequirePackage{url}

%
\RequirePackage{tocbibind}

% Manejo de colores
\RequirePackage{xcolor}

% Colores definidos en 'listings'
\definecolor{dkgreen}{rgb}{0,0.6,0}
\definecolor{gray}{rgb}{0.5,0.5,0.5}
\definecolor{mauve}{rgb}{0.58,0,0.82}
\definecolor{green}{rgb}{0, 173, 143}

% Colors grises (para la portada)
\definecolor{grisclar}{gray}{0.5}
\definecolor{grisfosc}{gray}{0.25}

% Para evitar líneas huérfanas y viudas
\clubpenalty=10000
\widowpenalty=10000


% Cabecera del documento
\newcommand{\tutor}[1]{\newcommand{\t@tor}{#1}}
\newcommand{\curs}[1]{\newcommand{\c@rs}{#1}}

\newcommand{\titulacioname}{Grado en Ingenier\'ia Inform\'atica}
\newcommand{\tfgname}{Trabajo fin de grado}

\newcommand{\authorname}{Realizado por}
\newcommand{\tutorname}{Director}
\newcommand{\coursename}{}%Curso
\newcommand{\keywordsname}{Palabras clave}

\newcommand{\algorithmname}{Algoritmos}
\newcommand{\listalgorithmname}{\'Indice de Algoritmos}
\newcommand{\quadrename}{Quadre}
\newcommand{\listquadrename}{\'Index de quadres}

\newcommand{\appendicesname}{Ap\'endice}


\addto\captionsspanish{%Español
     \renewcommand{\titulacioname}{GRADO EN INGENIER\'IA INFORM\'ATICA}
     \renewcommand{\tfgname}{TRABAJO FIN DE GRADO}
     \renewcommand{\authorname}{Realizado por}
     \renewcommand{\tutorname}{{Director}}
     \renewcommand{\coursename}{}%Curso
     \renewcommand{\keywordsname}{Palabras clave}
     \renewcommand{\tablename}{Tabla}
     \renewcommand{\listtablename}{\'Indice de tablas}
     \renewcommand{\algorithmname}{Algoritmo}
     \renewcommand{\listalgorithmname}{\'Indice de algoritmos}
     \renewcommand{\quadrename}{Cuadro}
     \renewcommand{\listquadrename}{\'Indice de cuadros}
     \renewcommand{\appendicesname}{Ap\'endices}}
     
\addto\captionsenglish{%Ingles 
     \renewcommand{\titulacioname}{DEGREE IN COMPUTER ENGINEERING}
     \renewcommand{\tfgname}{DEGREE FINAL WORK}
     \renewcommand{\authorname}{Author}
     \renewcommand{\tutorname}{Tutor}
     \renewcommand{\coursename}{Course}
     \renewcommand{\keywordsname}{Key words}
     \renewcommand{\algorithmname}{Algorithm}
     \renewcommand{\listalgorithmname}{List of algorithms}
     \renewcommand{\appendicesname}{Appendices}}
     

% Nuevos tipos de objetos flotantes
% Formato de los caption de tablas, figuras, etc.
% Líneas centrados, tamaño "small" para etiqueta y texto, y la etiqueta en negrita
\RequirePackage[justification=centerlast,small,bf,labelsep=colon]{caption}

\RequirePackage{algorithmic}
\RequirePackage[within=chapter]{newfloat}

\DeclareFloatingEnvironment[fileext=loa,
     listname=\listalgorithmname,name=\algorithmname]{algorithm}
     \DeclareCaptionFormat{ruled}{\hrulefill\par#1#2#3\vspace{-.5\baselineskip}\hrulefill}
\captionsetup[algorithm]{justification=raggedright,singlelinecheck=off,position=below,format=ruled}  
   
\DeclareFloatingEnvironment[fileext=loq,
     listname=\listquadrename,name=\quadrename]{quadre}     
  

% Para escribir código y que quede bien
\RequirePackage{listings}

\lstset{ %
	language=C++,				% choose the language of the code
	basicstyle=\footnotesize,			% the size of the fonts that are used for the code
	numbers=left,                  			% where to put the line-numbers
	numberstyle=\footnotesize,     		% the size of the fonts that are used for the line-numbers
	stepnumber=1,                   		% the step between two line-numbers. If it is 1 each line will be numbered
	numbersep=5pt,                  		% how far the line-numbers are from the code
	backgroundcolor=\color{white},		% choose the background color. 
	numberstyle=\tiny\color{gray},		% choose the color of the numbers
	keywordstyle=\color{blue},		% choose the color of the keywords
	commentstyle=\color{dkgreen},		% choose the color of the comments
	stringstyle=\color{mauve}, 		% choose the color of the strings
	showspaces=false,              		% show spaces adding particular underscores
	showstringspaces=false,         		% underline spaces within strings
	showtabs=false,                 		% show tabs within strings adding particular underscores
	frame=single,          			% adds a frame around the code
	tabsize=2,          				% sets default tabsize to 2 spaces
	captionpos=b,           			% sets the caption-position to bottom
	breaklines=true,        			% sets automatic line breaking
	breakatwhitespace=false,   		% sets if automatic breaks should only happen at whitespace
	escapeinside={\%*}{*)}         		% if you want to add a comment withi your code
}


% Aspecto los títulos para \chapter y \section, así como 
% añadir un punto después de la numeración de las secciones
\RequirePackage{titlesec}

\titlelabel{\thetitle.\quad}

\titleformat{\chapter}[display]
{\normalfont\Large\filleft\sffamily\bfseries}  			% FORMAT
{\titlerule[1pt]                                  
 \vspace{1pt}
 \titlerule
 \vspace{1pc}
 \LARGE\MakeUppercase{\chaptertitlename} \thechapter} 	% LABEL
{0pc}                                                 			        	% SEP
{\Huge}                                               				% BEFORE CODE (antes del titulo)
[\vspace{5pt}{\titlerule[1pt]}]                                        	% AFTER  CODE (despues del titulo)

\titleformat{\section}
 {\Large\normalfont\sffamily\bfseries}
 {\thesection}{0.5em}{}[\hrule height1.5pt]
%

\RequirePackage{tocloft}
%
\setlength{\cftbeforechapskip}{3pt}


% Definición de las cabeceras y pies de página
\RequirePackage{fancyhdr}

\fancyfoot[LE,RO]{\thepage}
\pagestyle{fancy}

\fancypagestyle{plain}{%
  \fancyhf{}%
  \renewcommand{\headrulewidth}{0pt}%
  \fancyhf[lef,rof]{\thepage}%
}

\setlength{\headheight}{16pt}
%\renewcommand*{\headheight}{14pt}
\renewcommand*{\headrulewidth}{0pt}

\renewcommand{\chaptermark}[1]{
		\markboth{
			{\sffamily
			\mdseries
			\normalsize
			\hspace{1em} #1}
		}{}}

\renewcommand{\sectionmark}[1]{
		\markright{
			{\sffamily
			\mdseries
			\normalsize
			\thesection
			\hspace{0.5em} #1}
		}{}}



%\lhead[\sffamily\MakeUppercase{\ifnum \thechapter>0 \chaptertitlename \ \thechapter. \fi} \leftmark]{}
%\rhead{\ifodd\value{page}
%		\sffamily\MakeUppercase{ 
%			\ifnum \thechapter>0 
%				\chaptertitlename \ \thechapter. 
%			\fi
%		} \leftmark \fi}
\lhead{}
\rhead{}
\cfoot{} 
%\lfoot{}
%\rfoot{}


\RequirePackage{graphicx}
\RequirePackage{eso-pic}


%  Navegable
\RequirePackage[colorlinks,
	linkcolor=black,
  	filecolor=black,
  	citecolor=black
	]{hyperref}


% Separación entre párrafos
\setlength{\parskip}{1ex}

\newcommand{\foreign}[2]{\foreignlanguage{#1}{\textit{#2}}}
     
\newenvironment{Foreign}[1]{\begin{quote}\itshape\selectlanguage{#1}}{\end{quote}}     
      

% Resumen
\newcommand{\keywords}[2]{%
   \addto\captionsspanish{\def\keywords{#1}}
   \addto\captionsenglish{\def\keywords{#2}}}

\newenvironment{abstract}[1][spanish]{%
	\begingroup
          \selectlanguage{#1}\addvspace{	\baselineskip}
	\par\hfill
          {\sffamily\bfseries\Huge\abstractname}\smallskip\par}{%
	\vskip 10mm
          \hrule\par\nopagebreak\noindent\textbf{\keywordsname:} \keywords\vspace*{3pt}
	\newpage
          \iflanguage{english}{\endgroup\makeindexes}{\endgroup\par\vspace{\stretch{0}}}}


% Índices
\newcommand{\makeindexes}{\cleardoublepage\tableofcontents\cleardoublepage
                          \iflistfigures\listoffigures\fi                                             
                          \addtocontents{toc}{\protect\noindent\protect\hrulefill\protect\par}}

\setlength{\cftaftertoctitleskip}{\baselineskip}
\setlength{\cftafterloftitleskip}{\baselineskip}
\setlength{\cftafterlottitleskip}{\baselineskip}

\renewcommand{\cfttoctitlefont}{\hfill\Huge\sffamily\bfseries}
\renewcommand{\cftlottitlefont}{\hfill\Huge\sffamily\bfseries}
\renewcommand{\cftloftitlefont}{\hfill\Huge\sffamily\bfseries}

\renewcommand{\cftaftertoctitle}{\par\noindent\hrulefill}
\renewcommand{\cftafterlottitle}{\par\noindent\hrulefill}
\renewcommand{\cftafterloftitle}{\par\noindent\hrulefill}

\newcommand{\APPENDIX}{\appendix
             \phantomsection
             \addtocontents{toc}{\protect\noindent\protect\hrulefill\protect\par}             
             \ifnum\value{appendix}=1 \addtocontents{toc}{\protect\appendixname\protect\par}
                    \else\addtocontents{toc}{\protect\appendicesname\protect\par}\fi}


\newcommand{\m@ketitle}{%
\begin{titlepage}

\centering

\vspace{\stretch{1}}

%TITULO
\LARGE\bfseries\@title

\begin{color}{grisfosc}
Departamento de arquitectura de computadores y autom\'atica
\end{color}

%IMAGEN
 \vfill
  \begin{center}    
      \includegraphics [width=75mm] {UCM}
  \end{center}
  \vfill

%CABECERA 1
\begin{color}{grisfosc}
\LARGE
\tfgname

\titulacioname
\end{color}

\vspace{\stretch{1}}

% Autor, tutor y fecha
\begin{color}{grisfosc}
  \LARGE\authorname:\\
\end{color}
	\LARGE\bfseries\@author\\[0.2cm]
\begin{color}{grisfosc}
   \LARGE\tutorname: \\
\end{color}
	\LARGE\bfseries\t@tor\\
\begin{color}{grisfosc}
   Co-director: \\
\end{color}
	Rafael Rubio D\'iaz
\medskip

\vspace{\stretch{1}}

%CABECERA 2
\begin{color}{grisfosc}
\LARGE
Facultad de  Inform\'atica

Universidad Complutense de Madrid

\LARGE\bfseries\coursename\space\c@rs
\end{color}

\end{titlepage}}


\renewcommand{\cleardoublepage}{\clearpage\if@twoside \ifodd\c@page
\else
\hbox{}
\thispagestyle{empty}
\newpage
\if@twocolumn\hbox{}\newpage\fi\fi\fi}

\AtBeginDocument{%
    \frontmatter\m@ketitle\cleardoublepage\thispagestyle{plain}}

\newcounter{appendix}
\AtEndDocument{%
    \ifnum\value{chapter}=1 
           \immediate\write\@auxout{\unexpanded{\setcounter{appendix}{1}}}
    \else 
           \immediate\write\@auxout{\unexpanded{\setcounter{appendix}{2}}} \fi}