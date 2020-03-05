#!/bin/sh

":" .length//;kotlinc -script "$0" "$@";exit
/*
 * As a shell script,
 * `":"` means nop, and `;` means the end of a command.
 * So `":" .length //` means nop.
 * `$0` means this file.
 * So `kotlinc -script "$0" "$@"` means "execute this file as a kotlinscript".
 *
 * As a kotlin script,
 * ":" means a string literal.
 * So it's `":" .length` (returns 1) and a comment.
 * `.length` suppresses a warning about an unused string literal.
 */

println("Hello, World!")
