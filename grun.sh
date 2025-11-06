#!/bin/sh
gui="java -Djava.awt.headless=false -cp DSL.jar org.antlr.v4.gui.TestRig DSL schedule -gui $1"
echo $gui
eval $gui
