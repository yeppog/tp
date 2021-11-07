---
layout: page
title: Koh Jia Xian's Project Portfolio Page

---
## Project: TaskMaster2103

TaskMaster2103 is an extension to [AddressBook - Level 3](https://se-education.org/addressbook-level3/), a CLI-based address book JavaFX application.
TaskMaster2103 offers task-related features, and a streamlined GUI to better accommodate your academic needs.

Given below are my contributions to the project.
- **New Feature**: Added AddressBook integration and GUI into tasks ([#81](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/88))
  - What it does: allows users to designate certain contacts to tasks
  - Justification: integration of AddressBook capabilities with TaskMaster2103 would help with the app's cohesiveness.
  - Highlights: tasks contacts are displayed differently based on whether these contacts are present in the AddressBook. The contacts' names and display are updated automatically with changes to any data.

- **New Feature**: Added the ability to purge tasks (Initial: [#69](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/69), Refactor: [#90](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/90))
  - What it does: allows users mass-delete tasks
  - Justification: users may want to clear out tasks. For example, work-related tasks after the semester is complete.
  - Highlights: unlike `clear`, purge tasks purge all currently-filtered or listed tasks; users are able to effortlessly designate what kind of task to delete.

- **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=koh-jx&tabRepo=AY2122S1-CS2103-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

- **Enhancements to new/existing features**
  - Implemented `task done`, and `undo` for some commands
  - Extended `edit` for tasks  ([#42](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/42))

- **Testing** ([#53](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/53), [#186](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/186), [#189](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/189))
  - Added test cases for `undo`, `redo`, `task done`, `task edit`, `task purge` and other task-related commands and parsers.
  - Integrated contacts into existing test cases
  - Set up test utils for task-related tests

- **Documentation**
  - Add documentation for contacts, `task edit`, NFRs and glossary in Developer Guide ([#74](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/74), [#194](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/194))
  - Add task-related commands, GUI instructions, and fix bugs in UG ([#163](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/163))

- Community:
  - PRs reviewed (with non-trivial comments) include [#175](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/175), [#79](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/79), [#58](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/58)
