// DSLParser.g4
parser grammar DSLParser;

options { tokenVocab=DSLParser; }

schedule: sequence+;

sequence: IDENTIFIER TIME_RANGE LEADS_TO place stop* end;

stop: LEADS_TO DURATION place;

place: PLACE WS? place?;

end: LEADS_TO DURATION;