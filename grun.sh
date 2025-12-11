#!/bin/sh
gui="java -Djava.awt.headless=false -cp target/DSL.jar org.antlr.v4.gui.TestRig generated.DSL schedule -gui $1"
echo $gui
eval $gui
