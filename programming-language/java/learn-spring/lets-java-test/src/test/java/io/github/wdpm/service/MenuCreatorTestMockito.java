package io.github.wdpm.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * mockito test example
 *
 * @author evan
 * @date 2020/5/19
 */
class MenuCreatorTestMockito {
    @Test
    public void WhenAMenuIsCreated_ThenDailySpecialServiceIsCalled() {
        //步骤1：创建Mock 对象
        DailySpecialService mockService = Mockito.mock(DailySpecialService.class);

        //步骤2：设置预期行为
        List<String> specials = new ArrayList<>();
        Mockito.when(mockService.getSpecials()).thenReturn(specials);

        //步骤3：注入Mock 对象
        MenuCreator menuCreator = new MenuCreator(mockService);

        //步骤4: 调用测试对象
        menuCreator.getTodayMenu();

        //步骤5：验证
        Mockito.verify(mockService, Mockito.times(1));
    }
}