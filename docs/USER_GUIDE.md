# User Guide

## Core Idea

NLH Preflop Manager is a local-first range builder. A strategy is stored as an action tree, and each action can hold its own 13x13 hand matrix.

## Building A Tree

1. Start from the root node.
2. Add actions for the current spot, such as `fold`, `call`, `raise`, or custom bet sizes.
3. Select an action to move deeper into the tree.
4. Repeat until the preflop line you want to study is represented.

## Editing Ranges

- Click grid cells to assign or clear hands for the selected action.
- Use absolute view when you want to inspect the selected node directly.
- Use weighted view when you want to see the effective range after previous branch frequencies.
- Use random mode when you want a quick weighted decision aid.

## Saving And Moving Work

- Use `Save` for quick local browser storage.
- Use `Load` to restore the latest local save.
- Use `Export` to download a JSON strategy file.
- Use `Import` to restore a JSON file from another browser or machine.

## Device Notes

The interface is desktop-first because range grids and trees need space, but it is responsive and usable on tablets and phones. On smaller screens, panels stack and the 13x13 grid scales to fit the viewport.
