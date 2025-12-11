// DSLLexer.g4
lexer grammar DSLLexer;

@header {
package generated;
}
                    
Number: Digits ('.' Digits)? ;

fragment Digits: ([0-9])+ ;
fragment HOUR: [0-2]?[0-9];
fragment MINUTE: [0-5]?[0-9];

IDENTIFIER: '<' (~'>')+ '>';
TIME_RANGE: '[' HOUR ':' MINUTE ' - ' HOUR ':' MINUTE ']';
LEADS_TO: '->';
DURATION: '(' Number ' min' ')';
PLACE: ~[ \t\r\n]+ ;
WS: [ \t\r\n]+ -> channel(HIDDEN);

