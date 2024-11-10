package test;

import data.DataContributors;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class AppTest extends BaseTest {

    private final By POPUP_MODAL_CLOSE_BUTTON = (By.xpath("//div[@class='splash-screen-header modal-header']"));
    private final By SIGNIN_BUTTON = (By.xpath("//span[contains(text(),'Sign In')]"));
    private final By REGISTRATION_BUTTON = By.xpath("//span[contains(text(),'Registration')]");
    private final By USERNAME_TEXTBOX = By.xpath("//input[@name='name']");
    private final By EMAIL_TEXTBOX = (By.xpath("//input[@name='email']"));
    private final By PHONE_TEXTBOX = By.xpath("//input[@name='phoneNumber']");
    private final By PASSWORD_TEXTBOX = (By.xpath("//input[@type='password']"));
    private final By CONFIRM_PASSWORD_TEXTBOX = By.xpath("//input[@name='confirmPassword']");
    private final By REGISTER_BUTTON = By.xpath("//button[@class='btn btn-primary']");
    private final By REGISTRATION_SUCCESS_MESSAGE = (By.xpath("//div[@class='alert alert-success fade show']"));
    private final By EXISTING_USER_MESSAGE = (By.xpath("//div[@class='alert alert-danger fade show']"));
    private final By LOGIN_BUTTON = (By.xpath("//div[@class='account-access-submit-button']"));
    private final By PROFILE_DROPDOWN = (By.xpath("//div[@class='show-student-profile']"));
    private final By PROFILE_BUTTON = (By.xpath("//nav//a//span[contains(text(),'Profile')]"));
    private final By YOUR_PROFILE_PAGE = (By.xpath("//h2[contains(text(),'Profile')]"));
    private final By FULL_NAME_TEXTBOX = (By.xpath("//input[@name='fullName']"));
    private final By PHONE_NUMBER_TEXTBOX = (By.xpath("//input[@name='phoneNumber']"));
    private final By DATE_OF_BIRTH_TEXTBOX = (By.xpath("//input[@name='dob']"));
    private final By GENDER_OPTION_FEMALE = (By.xpath("//label[@for='radio-gender-female']"));
    private final By GENDER_OPTION_MALE = (By.xpath("//label[@for='radio-gender-male']"));
    private final By EDUCATION_SELECT_COMBOBOX = (By.xpath("//select//option"));
    private final By UNIVERSITY_SELECT_COMBOBOX = (By.id("react-select-3-input"));
    private final By PROFILE_PICTURE_BUTTON = (By.xpath("//img[@class='rounded-circle avatar-sm']"));
    private final By PROFILE_PICTURE_UPLOAD = (By.xpath("//input[@type='file']"));
    private final By UPDATE_BUTTON = (By.xpath("//button[@class='profile-submit-page-btn btn btn-primary']"));
    private final By UPDATE_SUCCESS_MESSAGE = (By.xpath("//div[@class='Toastify__toast-icon Toastify--animate-icon Toastify__zoom-enter']"));

    // Registration Test with valid credentials //
    @Test(dataProvider = "userDataProvider", dataProviderClass = DataContributors.class)
    public void RegistrationTest(User user) {

        /**Registration Form Page*/

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.findElement(POPUP_MODAL_CLOSE_BUTTON).click();

        Assert.assertTrue(driver.findElement(REGISTRATION_BUTTON).isDisplayed(), "The Registration Button is not displayed");
        driver.findElement(REGISTRATION_BUTTON).click();

        driver.findElement(USERNAME_TEXTBOX).sendKeys(user.getUsername());
        Assert.assertEquals(driver.findElement(USERNAME_TEXTBOX).getAttribute("value"), user.getUsername(), "Name does not match");

        driver.findElement(EMAIL_TEXTBOX).sendKeys(user.getEmail());
        Assert.assertEquals(driver.findElement(EMAIL_TEXTBOX).getAttribute("value"), user.getEmail(), "Email does not match");

        driver.findElement(PHONE_TEXTBOX).sendKeys(user.getPhone());
        Assert.assertEquals(driver.findElement(PHONE_TEXTBOX).getAttribute("value"), user.getPhone(), "Phone does not match");

        driver.findElement(PASSWORD_TEXTBOX).sendKeys(user.getPassword());
        Assert.assertEquals(driver.findElement(PASSWORD_TEXTBOX).getAttribute("value"), user.getPassword(), "Password does not match");

        driver.findElement(CONFIRM_PASSWORD_TEXTBOX).sendKeys(user.getConfirmPassword());
        Assert.assertEquals(driver.findElement(CONFIRM_PASSWORD_TEXTBOX).getAttribute("value"), user.getConfirmPassword(), "Confirm Password does not match");

        Assert.assertTrue(driver.findElement(REGISTER_BUTTON).isDisplayed(), "The Register Button is not displayed");
        driver.findElement(REGISTER_BUTTON).click();

        // Checking if the user is already registered or not
        boolean isRegistered = false;
        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(REGISTER_BUTTON));
                if (driver.findElement(REGISTRATION_SUCCESS_MESSAGE).isDisplayed()) {
                    isRegistered = true;
                    break;
                }
            } catch (Exception e) {
                // Retry if not successful
            }
        }

        if (isRegistered) {
            Assert.assertTrue(driver.findElement(REGISTRATION_SUCCESS_MESSAGE).isDisplayed());
            String registrationSuccessMessageText = driver.findElement(REGISTRATION_SUCCESS_MESSAGE).getText();
            System.out.println(registrationSuccessMessageText);
        } else {
            Assert.assertTrue(driver.findElement(EXISTING_USER_MESSAGE).isDisplayed());
            String existingUserMessageText = driver.findElement(EXISTING_USER_MESSAGE).getText();
            System.out.println(existingUserMessageText);
        }
    }


    // Login Test & Profile Update Test with valid credentials //
    @Test(dataProvider = "lastUserDataProvider", dataProviderClass = DataContributors.class)
    public void LoginTest(User user) {

        final String updated_profile_uname = "Malissa Russo";
        final String updated_phone_no = "0612385574";
        final String updated_uni_name = "Brac University";
        final String updated_day = "18";
        final String updated_month = "11";
        final String updated_year = "2023";

        /**Login Form Page*/

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(POPUP_MODAL_CLOSE_BUTTON));
        Assert.assertTrue(driver.findElement(POPUP_MODAL_CLOSE_BUTTON).isDisplayed(), "The Popup Modal is not displayed"); // Validating if the Popup is displayed
        driver.findElement(POPUP_MODAL_CLOSE_BUTTON).click();

        Assert.assertTrue(driver.findElement(SIGNIN_BUTTON).isDisplayed(), "The Sign In Button is not displayed"); // Validating if the SignIn Button is displayed
        driver.findElement(SIGNIN_BUTTON).click();
        driver.findElement(EMAIL_TEXTBOX).sendKeys(user.getEmail());
        Assert.assertTrue(driver.findElement(EMAIL_TEXTBOX).getAttribute("value").contains(user.getEmail())); // Validating if the Email is entered correctly

        driver.findElement(PASSWORD_TEXTBOX).sendKeys(user.getPassword());
        Assert.assertTrue(driver.findElement(PASSWORD_TEXTBOX).getAttribute("value").contains(user.getPassword())); // Validating if the Password is entered correctly

        Assert.assertTrue(driver.findElement(LOGIN_BUTTON).isDisplayed(), "The Login Button is not displayed"); // Validating if the Login Button is displayed
        driver.findElement(LOGIN_BUTTON).click();


        /**Student Profile Update Page*/

        wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_DROPDOWN));
        Assert.assertTrue(driver.findElement(PROFILE_DROPDOWN).isDisplayed(), "The Profile Dropdown is not displayed"); // Validating if the Profile Dropdown is displayed
        driver.findElement(PROFILE_DROPDOWN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_BUTTON));
        Assert.assertTrue(driver.findElement(PROFILE_BUTTON).isDisplayed(), "The Profile Button is not displayed"); // Validating if the Profile Button is displayed
        driver.findElement(PROFILE_BUTTON).click();
        Assert.assertTrue(driver.findElement(YOUR_PROFILE_PAGE).isDisplayed(),"Your Profile page is not displayed"); // Validating Your Profile page is displayed

        wait.until(ExpectedConditions.visibilityOfElementLocated(FULL_NAME_TEXTBOX));
        driver.findElement(FULL_NAME_TEXTBOX).clear();
        driver.findElement(FULL_NAME_TEXTBOX).sendKeys(updated_profile_uname);

        //Validating if the Name is entered correctly
        Assert.assertTrue(driver.findElement(FULL_NAME_TEXTBOX).getAttribute("value").contains(updated_profile_uname), "The Name does not matches");

        driver.findElement(PHONE_NUMBER_TEXTBOX).clear();
        driver.findElement(PHONE_NUMBER_TEXTBOX).sendKeys(updated_phone_no);

        //Validating if the Phone Number is entered correctly
        Assert.assertTrue(driver.findElement(PHONE_NUMBER_TEXTBOX).getAttribute("value").contains(updated_phone_no), "The Phone Number does not matches");

        //Updating Date of Birth
        driver.findElement(DATE_OF_BIRTH_TEXTBOX).clear();
        driver.findElement(DATE_OF_BIRTH_TEXTBOX).sendKeys(updated_day);
        driver.findElement(DATE_OF_BIRTH_TEXTBOX).sendKeys(Keys.ARROW_RIGHT);
        driver.findElement(DATE_OF_BIRTH_TEXTBOX).sendKeys(updated_year);
        driver.findElement(DATE_OF_BIRTH_TEXTBOX).sendKeys(Keys.ARROW_LEFT);
        driver.findElement(DATE_OF_BIRTH_TEXTBOX).sendKeys(updated_month);

        //Selecting Gender
        WebElement maleRadioButton = driver.findElement(GENDER_OPTION_MALE);
        WebElement femaleRadioButton = driver.findElement(GENDER_OPTION_FEMALE);

        boolean selectfemale = true;

        if (selectfemale) {
            femaleRadioButton.click(); // This will select the Female option
        } else {
            maleRadioButton.click(); // This will select the Male option
        }

        //Updating Profile Picture
        wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_PICTURE_BUTTON));
        WebElement fileInput = driver.findElement(PROFILE_PICTURE_UPLOAD); //Locating the picture upload input field
        String filePath = new File("src/test/resources/img.png").getAbsolutePath(); //Getting the absolute path of the image
        fileInput.sendKeys(filePath); //Uploading the image

        //Validating if the Profile Picture is updated correctly
        Assert.assertTrue(fileInput.getAttribute("value").contains("img.png"), "The Profile Picture is updated correctly");

        List<WebElement> options = driver.findElements(EDUCATION_SELECT_COMBOBOX);
        options.get(6).click(); //Selecting the desired Education Level from the dropdown
        Assert.assertTrue(options.get(6).isSelected(), "The Education Level is not selected"); //Validating if the Education Level is selected

        wait.until(ExpectedConditions.visibilityOfElementLocated(UNIVERSITY_SELECT_COMBOBOX));
        driver.findElement(UNIVERSITY_SELECT_COMBOBOX).click();
        driver.findElement(UNIVERSITY_SELECT_COMBOBOX).sendKeys(updated_uni_name); //Typing the desired University Name from the dropdown

        //Validating if the University Name is entered correctly
        Assert.assertTrue(driver.findElement(UNIVERSITY_SELECT_COMBOBOX).getAttribute("value").contains(updated_uni_name), "The University Name does not matches");
        driver.findElement(UNIVERSITY_SELECT_COMBOBOX).sendKeys(Keys.ENTER); //Pressing Enter to select the typed University Name

        driver.findElement(UPDATE_BUTTON).submit();
        wait.until(ExpectedConditions.visibilityOfElementLocated(UPDATE_BUTTON));

        //Validating if the Profile is updated successfully
        Assert.assertTrue(driver.findElement(UPDATE_SUCCESS_MESSAGE).isDisplayed(), "Profile update success message is not displayed");
    }
}

