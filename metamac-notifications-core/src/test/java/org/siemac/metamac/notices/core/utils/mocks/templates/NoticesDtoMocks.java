package org.siemac.metamac.notices.core.utils.mocks.templates;

import java.util.Arrays;
import java.util.Date;

import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;

public class NoticesDtoMocks extends MetamacMocks {

    public static NoticeDto mockAnnouncementWithReceivers() {
        NoticeDto noticeDto = buildAnnouncement();
        for (int i = 0; i < 5; i++) {
            ReceiverDto receiver = new ReceiverDto();
            receiver.setUsername("user" + i);
            noticeDto.addReceiver(receiver);
        }
        return noticeDto;
    }

    public static NoticeDto mockAnnouncementWithConditions() {
        NoticeDto noticeDto = buildAnnouncement();
        noticeDto.setApplications(Arrays.asList("app-1", "app-2", "app-3"));
        noticeDto.setRoles(Arrays.asList("role1", "role2"));
        noticeDto.setStatisticalOperationCodes(Arrays.asList("op1", "op2", "op3", "op4"));
        return noticeDto;
    }

    // --------------------------------------------------------
    // PRIVATE UTILITY METHODS
    // --------------------------------------------------------

    private static NoticeDto buildAnnouncement() {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setSubject(mockString(10));
        noticeDto.setExpirationDate(new Date());
        noticeDto.setSendingApplication("application");
        noticeDto.setSendingUser("user");
        noticeDto.setType(NoticeType.ANNOUNCEMENT);
        return noticeDto;
    }
}
