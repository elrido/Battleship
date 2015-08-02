/**
 * Battleship game implemented with Swing UI in Java
 * Copyright (C) 2015 Simon Rupf <simon@rupf.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.rupf.battleship;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaOutputStream extends OutputStream {

   private final JTextArea textArea;
   private final StringBuilder sb = new StringBuilder();

   public TextAreaOutputStream(final JTextArea textArea) {
      this.textArea = textArea;
   }

   @Override
   public void flush() {
   }

   @Override
   public void close() {
   }

   @Override
   public void write(int b) throws IOException {

      if (b == '\r')
         return;

      if (b == '\n') {
         final String text = sb.toString() + "\n";
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               textArea.append(text);
            }
         });
         sb.setLength(0);
         return;
      }

      sb.append((char) b);
   }
}
