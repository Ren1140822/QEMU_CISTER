/**
 * The package contains classes relate to the concept of Parser. A
 * {@link Parsers.Parser} is responsible for changing the format of a piece of
 * data. In this case, it transforms a {@link Commands.Command} into another
 * one.
 * <br/><br/>
 * For the time being there is only one parser, i.e. {@link Parsers.CMDtoJSON},
 * which is responsible to transform the commands introduced as they would be in
 * the QEMU Monitor to the matching JSON format that QEMU understands.
 */
package Parsers;
