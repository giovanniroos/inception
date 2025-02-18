/*
 * Copyright 2022 Marcus Portmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package digital.inception.core.wbxml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

/**
 * The <b>CDATA</b> class stores the data for a CDATA (Character Data) content type in a WBXML
 * document.
 *
 * <p>This content type is used to store a text string.
 *
 * @author Marcus Portmann
 */
public class CDATA implements Serializable, Content {

  private static final long serialVersionUID = 1000000;

  /** The text content. */
  private String text;

  /** Constructs a new empty <b>CDATA</b>. */
  public CDATA() {
    text = "";
  }

  /**
   * Constructs a new <b>CDATA</b> containing the specified text.
   *
   * @param text the text content
   */
  public CDATA(String text) {
    this.text = text;
  }

  /**
   * Returns the text content for the <b>CDATA</b> instance.
   *
   * @return the text content for the <b>CDATA</b> instance
   */
  public String getText() {
    return text;
  }

  /**
   * Print the content using the specified indent level.
   *
   * @param indent the indent level
   */
  public void print(int indent) {
    print(System.out, indent);
  }

  /**
   * Print the content to the specified <b>OutputStream</b> using the specified indent level.
   *
   * @param out the <b>OuputStream</b> to output the content to
   * @param indent the indent level
   */
  public void print(OutputStream out, int indent) {
    PrintStream pout = new PrintStream(out);

    pout.print(text);

    /*
     *  NOTE: don't close pout - it will close out (the underlying outputstream)
     * See API - PrintStream.close().
     */
  }

  /**
   * Set the text content for the <b>CDATA</b> instance.
   *
   * @param text the text content for the <b>CDATA</b> instance
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @return the string representation of the <b>CDATA</b> instance
   * @see Object#toString()
   */
  @Override
  public String toString() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      print(baos, 0);

      String result = baos.toString();

      baos.close();

      return result;
    } catch (Exception e) {
      return super.toString();
    }
  }
}
