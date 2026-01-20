package io.github.wdpm.service;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyMock test example
 *
 * @author evan
 * @date 2020/5/19
 */
class MenuCreatorTestEasyMock {
    @Test
    public void WhenAMenuIsCreated_ThenDailySpecialServiceIsCalled() {
        //步骤1：创建Mock 对象
        DailySpecialService mockService = EasyMock.createMock(DailySpecialService.class);

        //步骤2：设置预期行为
        List<String> specials = new ArrayList<>();
        EasyMock.expect(mockService.getSpecials()).andReturn(specials).once();// once() means call once time
        EasyMock.replay(mockService);

        //步骤3：注入Mock 对象
        MenuCreator menuCreator = new MenuCreator(mockService);

        //步骤4：调用测试对象
        menuCreator.getTodayMenu();

        //步骤5：验证
        EasyMock.verify(mockService);
    }
}