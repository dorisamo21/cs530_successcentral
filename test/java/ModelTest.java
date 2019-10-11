import com.ccsu.cs530.successcentral.model.Match;
import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import static junit.framework.TestCase.fail;

@RunWith(JUnit4.class)
public class ModelTest {

    @Test
    public void testThatAllGettersCanBeCalledWithoutThrowingExceptions() throws IntrospectionException {
        User user = new User();
        // TODO Refactor this to account for every business object.

        // Use reflection to get all the getter methods
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(User.class, Object.class).getPropertyDescriptors();
        for (int i = 0; i < propDescArr.length; i++) {
            try {
                PropertyDescriptor pd = propDescArr[i];
                pd.getReadMethod().invoke(user);
            } catch (Exception e) {
                fail("Hey CCSU software engineer. One of your getter on the User object is throwing an exception. Please fix!");
            }
        }

        Mentee mentee = new Mentee();
        propDescArr = Introspector.getBeanInfo(Mentee.class, Object.class).getPropertyDescriptors();
        for (int i = 0; i < propDescArr.length; i++) {
            try {
                PropertyDescriptor pd = propDescArr[i];
                pd.getReadMethod().invoke(mentee);
            } catch (Exception e) {
                fail("Hey CCSU software engineer. One of your getter on the Mentee object is throwing an exception. Please fix!");
            }

        }

        Mentor mentor = new Mentor();
        propDescArr = Introspector.getBeanInfo(Mentor.class, Object.class).getPropertyDescriptors();
        for (int i = 0; i < propDescArr.length; i++) {
            try {
                PropertyDescriptor pd = propDescArr[i];
                pd.getReadMethod().invoke(mentor);
            } catch (Exception e) {
                fail("Hey CCSU software engineer. One of your getters on the Mentor object is throwing an exception. Please fix!");
            }
        }

        Match match = new Match();
        propDescArr = Introspector.getBeanInfo(Match.class, Object.class).getPropertyDescriptors();
        for (int i = 0; i < propDescArr.length; i++) {
            try {
                PropertyDescriptor pd = propDescArr[i];
                pd.getReadMethod().invoke(match);
            } catch (Exception e) {
                fail("Hey CCSU software engineer. One of your getters on the Match object is throwing an exception. Please fix!");
            }
        }
    }
}