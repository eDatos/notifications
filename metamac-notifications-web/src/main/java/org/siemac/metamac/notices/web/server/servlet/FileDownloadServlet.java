package org.siemac.metamac.notices.web.server.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.web.common.server.servlet.FileDownloadServletBase;
import org.siemac.metamac.web.common.shared.utils.SharedTokens;

public class FileDownloadServlet extends FileDownloadServletBase {

    private static final long           serialVersionUID = 2200261485966957131L;

    private NoticesConfigurationService configurationService;

    @Override
    protected File getFileToDownload(HttpServletRequest request) throws Exception {
        if (!StringUtils.isEmpty(request.getParameter(SharedTokens.PARAM_DOC))) {
            return getFileFromDocs(request.getParameter(SharedTokens.PARAM_DOC));
        } else if (!StringUtils.isEmpty(request.getParameter(SharedTokens.PARAM_FILE_NAME))) {
            return getFileFromTempDir(request.getParameter(SharedTokens.PARAM_FILE_NAME));
        } else {
            throw new InvalidArgumentException("You must specify some action");
        }

    }

    private File getFileFromDocs(String fileName) throws Exception {
        checkValidFileName(fileName);
        String docsPath = getConfigurationService().retrieveDocsPath();
        return new File(docsPath + File.separatorChar + fileName);
    }

    private NoticesConfigurationService getConfigurationService() {
        if (configurationService == null) {
            configurationService = ApplicationContextProvider.getApplicationContext().getBean(NoticesConfigurationService.class);
        }
        return configurationService;
    }
}
