/*
 * Copyright (C) 2013 Redpill Linpro AB
 *
 * This file is part of LibreOffice online edit module for Alfresco
 *
 * LibreOffice online edit module for Alfresco is free software: 
 * you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LibreOffice online edit module for Alfresco is distributed in the 
 * hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with LibreOffice online edit module for Alfresco. 
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.redpill_linpro.libreoffice;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;

/**
 * Linux implementation of the LibreOffice launcher. Tries a few well-known
 * installation locations for LibreOffice.
 * 
 * @author Marcus Svensson <marcus.svensson (at) redpill-linpro.com>
 * 
 */
public class LibreOfficeLauncherLinuxImpl implements LibreOfficeLauncher {
  @Override
  public void launchLibreOffice(String cmisUrl, String repositoryId, String filePath, String webdavUrl) {
    Runtime rt = Runtime.getRuntime();
    try {
      String params;
      if (null != webdavUrl && webdavUrl.length() > 0) {
        params = LibreOfficeLauncherHelper.generateLibreOfficeWebdavOpenUrl(webdavUrl);
      } else {
        params = LibreOfficeLauncherHelper.generateLibreOfficeCmisOpenUrl(cmisUrl, repositoryId, filePath);
      }
      StringBuffer cmd = new StringBuffer();
      try {
        String[] binaryLocations = { "soffice", "/usr/bin/soffice" };

        for (int i = 0; i < binaryLocations.length; i++) {
          cmd.append((i == 0 ? "" : " || ") + binaryLocations[i] + " \"" + params + "\" ");
        }

        System.out.println("Command: sh -c " + cmd);
        
        rt.exec(new String[] { "sh", "-c", cmd.toString() });        

        System.out.println("Process started");
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Failed to start LibreOffice, commandline: " + cmd.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
      }

    } catch (UnsupportedEncodingException e1) {
      JOptionPane.showMessageDialog(null, "Invalid URL for LibreOffice", "Error", JOptionPane.ERROR_MESSAGE);
      e1.printStackTrace();
    }
  }

}
