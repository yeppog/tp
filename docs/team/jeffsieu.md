---
layout: page
title: Jeff Sieu's Project Portfolio Page

---
## Project: TaskMaster2103

TaskMaster2103 is an extension to [AddressBook - Level 3](https://se-education.org/addressbook-level3/), a CLI-based address book JavaFX application.
It adds task-tracking functionalities to the base application, while boasting intuitive GUI features that complement the main CLI workflow.

Given below are my contributions to the project.

- **New feature**: Added the ability to filter tasks by tags (GUI: [`#57`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/57), CLI: [`#75`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/75), [`#144`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/144)).
  - What it does: allows the user to filter the main task list by certain conditions.
  - Justification: the user may have many tasks; allowing a way to quickly narrow down the scope of displayed tasks is beneficial to efficiency.
  - Highlights: filters are implemented as abstract classes to allow for extensibility (e.g., filter by date range).
- **New feature**: Added a GUI dialog interface to create/edit tasks ([`#86`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/86), [`#179`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/179), [`#185`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/185)).
  - What it does: allows the user to create/edit tasks using a graphical interface
  - Justification: the CLI version of the interaction requires all fields of the editied task to be specified of the task. The GUI allows the user the edit a single field without having to re-specify other existing fields.
  - Highlights: All GUI interactions route their logic in the same way as a CLI command, reducing the need for repeated code.
- **Code reorganization**: Overhauled the command parsing logic for **more robust and extensible command handling** ([`#175`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/175)). New commands can be easily added, command format errors are more succinct. The help window and usage messages are also automatically generated.
  - What it does: parses all instances of `abc/def` as a command argument with prefix `abc/` and value `def`.
  This is to prevent unexpected behaviour with the base application, where `/` was used in all prefixes but could also be present in data values.
  - Justification: Outdated usage messages were common as they were hard-coded strings. Commands knew nothing about their syntax, and handled arguments individually. This caused lots of logic repetition and room for error.
  - Highlights: To solve this, ***every command and command parser*** had to be refactored to include information about every parameter accepted, and their *multiplicity* (optional or not, can accept multiple or not). Once this was done, however, it allowed for many useful abstractions like auto-generation of command usage message, and detailed error messages that tell the user how exactly the format is wrong.
- **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=jeffsieu&tabRepo=AY2122S1-CS2103-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)
- **Enhancement to existing features**
  - Rethemed the GUI to have a more uniform aesthetic ([`#179`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/179)).
  - Revamped the help window to be auto-generated ([`#184`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/184)).
  - Implemented slash-to-focus on the command input ([`#56`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/56)).

- **Testing**
  - Added test cases for task-related commands ([`#58`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/58)).

- **Documentation**
  - Added `task add`, `task edit` and `task delete` use cases in [`#32`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/32)
  - Added `task filter` in Developer Guide ([`#71`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/71), [`#77`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/77)).

- **GUI**
  - Added GUI task list with checkboxes ([`#40`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/40), [`#51`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/51), [`#57`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/57)).
- Community:
  - PRs reviewed (with non-trivial comments): [`#44`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/44), [`#70`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/70), [`#89`](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/89)
