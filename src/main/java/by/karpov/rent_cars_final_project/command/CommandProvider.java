package by.karpov.rent_cars_final_project.command;


import by.karpov.rent_cars_final_project.command.impl.CodeEntryCommand;
import by.karpov.rent_cars_final_project.command.impl.SignInCommand;
import by.karpov.rent_cars_final_project.command.impl.SignUpCommand;
import by.karpov.rent_cars_final_project.command.impl.change.*;
import by.karpov.rent_cars_final_project.command.impl.change.admin.*;
import by.karpov.rent_cars_final_project.command.impl.find.FindOrderByIdCommand;
import by.karpov.rent_cars_final_project.command.impl.page.*;
import by.karpov.rent_cars_final_project.command.impl.page.admin.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

import static by.karpov.rent_cars_final_project.command.CommandType.*;


public final class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger(CommandProvider.class);

    private static final CommandProvider INSTANCE = new CommandProvider();
    private final EnumMap<CommandType, Command> commands = new EnumMap(CommandType.class);

    private CommandProvider() {
        initializeCommonCommands();
        initializeSignUpCommands();
        initializeCommonChangeCommands();
        initializeSignUpCommands();
        initializeAdminCommands();
        initializeAdminChangeCommands();
    }

    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    public Command getCommand(String commandName) {
        if (commandName == null) {
            LOGGER.warn("command name is null. The command was assigned the home page");
            return commands.get(TO_HOME_PAGE_COMMAND);
        }
        CommandType commandType;
        try {
            commandType = valueOf(commandName.toUpperCase());
            LOGGER.info("command type - {}", commandType);
        } catch (IllegalArgumentException e) {
            commandType = TO_HOME_PAGE_COMMAND;
            LOGGER.error("error! The command was assigned the home page. ", e);
        }
        return commands.get(commandType);
    }

    private void initializeCommonCommands() {
        commands.put(TO_HOME_PAGE_COMMAND, new GoToHomePageCommand());
        commands.put(SIGN_IN_PAGE, new SignInCommand());
        commands.put(TO_SIGN_IN_PAGE_COMMAND, new GoToSignInPageCommand());
        commands.put(SIGN_OUT_COMMAND, new SignOutCommand());
        commands.put(TO_PERSONAL_PROFILE_PAGE_COMMAND, new GoToPersonalProfilePageCommand());
      //  commands.put(MAKE_ORDER_PAGE, new MakeOrderCommand());
        commands.put(TO_MAKE_ORDER_PAGE_COMMAND, new GoToMakeOrderPageCommand());
     //   commands.put(PAYMENT_ENTRY_PAGE, new PaymentCommand());
       // commands.put(TO_PAYMENT_ENTRY_PAGE_COMMAND, new GoToPaymentEntryPageCommand());
      //  commands.put(FIND_MANUFACTURER_BY_ID_COMMAND, new FindCarByManufacturerCommand());
    }


    private void initializeSignUpCommands() {
        commands.put(SIGN_UP_PAGE, new SignUpCommand());
        commands.put(TO_SIGN_UP_PAGE_COMMAND, new GoToSignUpPageCommand());
        commands.put(CODE_ENTRY_PAGE, new CodeEntryCommand());
        commands.put(TO_CODE_ENTRY_PAGE_COMMAND, new GoToCodeEntryPageCommand());
        commands.put(SIGN_IN_PAGE, new SignInCommand());
        commands.put(TO_SIGN_IN_PAGE_COMMAND, new GoToSignInPageCommand());
        commands.put(SIGN_OUT_COMMAND, new SignOutCommand());
    }

    private void initializeAdminCommands() {
        commands.put(TO_ADMIN_USERS_PAGE_COMMAND, new GoToAdminUsersPageCommand());
        commands.put(TO_ADMIN_ORDERS_PAGE_COMMAND, new GoToAdminOrdersPageCommand());
        commands.put(TO_ADMIN_CARS_PAGE_COMMAND, new GoToAdminCarsPageCommand());
        commands.put(ADMIN_ADD_CAR_PAGE, new AdminAddCarCommand());
        commands.put(TO_ADMIN_ADD_CAR_PAGE_COMMAND, new GoToAdminAddCarPageCommand());
        commands.put(FIND_ORDER_BY_ID_COMMAND, new FindOrderByIdCommand());
    }

    private void initializeCommonChangeCommands() {
        commands.put(CHANGE_LANGUAGE_TO_ENGLISH_COMMAND, new ChangeLanguageToEnglishCommand());
        commands.put(CHANGE_LANGUAGE_TO_RUSSIAN_COMMAND, new ChangeLanguageToRussianCommand());
        commands.put(TO_CHANGE_FIRST_NAME_PAGE_COMMAND, new GoToChangeFirstNamePageCommand());
        commands.put(CHANGE_FIRST_NAME_PAGE, new ChangeFirstNameCommand());
        commands.put(TO_CHANGE_LAST_NAME_PAGE_COMMAND, new GoToChangeLastNamePageCommand());
        commands.put(CHANGE_LAST_NAME_PAGE, new ChangeLastNameCommand());
        commands.put(TO_CHANGE_PHONE_NUMBER_PAGE_COMMAND, new GoToChangePhoneNumberPageCommand());
        commands.put(CHANGE_PHONE_NUMBER_PAGE, new ChangePhoneNumberCommand());
        commands.put(TO_CHANGE_EMAIL_PAGE_COMMAND, new GoToChangeEmailPageCommand());
        commands.put(CHANGE_EMAIL_PAGE, new ChangeEmailCommand());
        commands.put(TO_CHANGE_PASSWORD_PAGE_COMMAND, new GoToChangePasswordPageCommand());
        commands.put(CHANGE_PASSWORD_PAGE, new ChangePasswordCommand());

    }

    private void initializeAdminChangeCommands() {
        commands.put(CHANGE_USER_STATUS_COMMAND, new ChangeUserStatusCommand());
        commands.put(CHANGE_USER_ROLE_COMMAND, new ChangeUserRoleCommand());
        commands.put(DELETE_USER_COMMAND, new DeleteUserCommand());
        commands.put(DELETE_CAR_COMMAND, new DeleteCarCommand());
        commands.put(CHANGE_CAR_COST_COMMAND, new ChangeCarCostCommand());
        commands.put(CHANGE_CAR_STATUS_COMMAND, new ChangeCarStatusCommand());
        commands.put(CHANGE_ORDER_STATUS_COMMAND, new ChangeOrderStatus());

    }
}