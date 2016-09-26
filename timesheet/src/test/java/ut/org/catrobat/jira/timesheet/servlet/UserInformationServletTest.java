package ut.org.catrobat.jira.timesheet.servlet;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.mock.component.MockComponentWorker;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.websudo.WebSudoManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import org.catrobat.jira.timesheet.activeobjects.ConfigService;
import org.catrobat.jira.timesheet.services.PermissionService;
import org.catrobat.jira.timesheet.servlet.UserInformationServlet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInformationServletTest {

    private UserInformationServlet userInformationServlet;

    private LoginUriProvider loginUriProvider;
    private TemplateRenderer templateRenderer;
    private PermissionService permissionService;
    private UserManager userManager;
    private WebSudoManager webSudoManager;
    private ConfigService configService;
    private ComponentAccessor componentAccessor;

    private HttpServletResponse response;
    private HttpServletRequest request;

    String test_key = "test_key";
    private ApplicationUser user;

    @Before
    public void setUp() throws Exception {
        new MockComponentWorker().init();

        loginUriProvider = Mockito.mock(LoginUriProvider.class);
        templateRenderer = Mockito.mock(TemplateRenderer.class);
        userManager = Mockito.mock(UserManager.class);
        webSudoManager = Mockito.mock(WebSudoManager.class);
        permissionService = Mockito.mock(PermissionService.class);
        componentAccessor = Mockito.mock(ComponentAccessor.class);
        user = Mockito.mock(ApplicationUser.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);

        userInformationServlet = new UserInformationServlet(loginUriProvider, templateRenderer, webSudoManager, permissionService);

        Mockito.when(user.getUsername()).thenReturn("test");
        Mockito.when(user.getKey()).thenReturn(test_key);

        Mockito.when(permissionService.checkIfUserExists(request)).thenReturn(user);

        //Mockito.when(userManager.getRemoteUser(request)).thenReturn(user);
        //Mockito.when(userManager.getUserProfile(test_key)).thenReturn(user);

        Mockito.when(permissionService.checkIfUserIsGroupMember(request, "jira-administrators")).thenReturn(false);
        Mockito.when(permissionService.checkIfUserIsGroupMember(request, "Timesheet")).thenReturn(true);
    }

    @Test
    public void testDoGet() throws Exception {
        userInformationServlet.doGet(request, response);
    }
}
