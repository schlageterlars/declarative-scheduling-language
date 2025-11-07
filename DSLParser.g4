// DSLParser.g4
parser grammar DSLParser;

options { tokenVocab=DSLLexer; }

schedule: sequence+ ;

sequence: identifier time_range LEADS_TO place stop* end? ;

identifier: IDENTIFIER ;

time_range: TIME_RANGE ;

duration: DURATION ;

stop: LEADS_TO duration place ;

place: (PLACE WS?)+ ;

end: LEADS_TO duration ;