/*
 * Entagged Audio Tag library
 * Copyright (c) 2003-2005 Raphaël Slinckx <raphael@slinckx.net>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *  
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package dalsong.mp3info;

public class VBRIMPEGFrame implements VbrInfoFrame {

	/**  the filesize in bytes */
	private int fileSize = 0;

	/**  The number of mpeg frames in the mpeg file */
	private int frameCount = 0;

	/**  Flag to determine if it is a valid VBRI Mpeg frame */
	private boolean isValidVBRIMPEGFrame = true;


	public VBRIMPEGFrame(byte[] bytes) {
		String vbri = new String( bytes, 0, 4 );
		if ( vbri.equals( "VBRI" )) {
			int offset = 4+6;
			fileSize = (bytes[offset] << 24)&0xFF000000 | (bytes[offset+1] << 16)&0x00FF0000 | (bytes[offset+2] << 8)&0x0000FF00 | bytes[offset+3]&0x000000FF;
			
			offset += 4;
			frameCount = (bytes[offset] << 24)&0xFF000000 | (bytes[offset+1] << 16)&0x00FF0000 | (bytes[offset+2] << 8)&0x0000FF00 | bytes[offset+3]&0x000000FF;
		}
		else
			//No frame VBR MP3 XING
			isValidVBRIMPEGFrame = false;

	}

	public int getFrameCount() {
		return frameCount;
	}

	public boolean isValid() {
		return isValidVBRIMPEGFrame;
	}

	public int getFileSize() {
	    return this.fileSize;
	}
	
	public String toString() {
		String output;

		if ( isValidVBRIMPEGFrame ) {
			output = "\n----VBRIMPEGFrame--------------------\n";
			output += "Frame count:" + frameCount + "\tFile Size:" + fileSize + "\n";
			output += "--------------------------------\n";
		}
		else
			output = "\n!!!No Valid VBRI MPEG Frame!!!\n";
		return output;
	}

	public boolean isVbr() {
		return true;
	}
}

