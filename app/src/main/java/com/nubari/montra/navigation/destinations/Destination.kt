package com.nubari.montra.navigation.destinations

sealed class Destination(val name: String, val route: String) {

    // Auth destinations
    object SignupDestination : Destination(name = "signup", route = "signup")
    object OnBoardingDestination : Destination(name = "onboarding", route = "onboarding")
    object LoginDestination : Destination(name = "login", route = "login")
    object ForgotPasswordDestination :
        Destination(name = "forgot password", route = "forgot_password")

    object VerificationDestination : Destination(name = "verification", route = "verification")

    // OnBoarding destinations
    object AccountSetupFormDestination :
        Destination(name = "set up account", route = "setup_account")

    object AccountSetupCompleteDestination :
        Destination(name = "account setup complete", route = "account_setup_complete")

    // Start Screen Destinations
    object Home : Destination(name = "home", route = "home")
    object Transactions : Destination(name = "transactions", route = "transactions")
    object Budget : Destination(name = "budget", route = "budget")
    object Profile : Destination(name = "profile", route = "profile")


    //Transaction Destinations
    object NewTransaction : Destination(name = "new transaction", route = "new_transaction")
    object TransactionReportPreview :
        Destination(name = "transaction report preview", route = "transaction_report_preview")

    object TransactionReport :
        Destination(name = "transaction report", route = "transaction_report")

    object TransactionDetail :
        Destination(name = "transaction detail", route = "transaction_detail")

    // Budget Destinations
    object BudgetForm : Destination(name = "budget form", route = "budget_form")
    object BudgetDetail : Destination(name = "budget detail", route = "budget_detail")

    object UserAccounts : Destination(name = "user accounts", route = "user_accounts")
    object AllSettings : Destination(name = "all settings", route = "all_settings")
    object CurrencySettings : Destination(name = "currency settings", route = "currency_settings")
    object LanguageSettings : Destination(name = "language settings", route = "language_settings")
    object NotificationSettings :
        Destination(name = "notification settings", route = "notification_settings")

    object ExportDataForm :
        Destination(name = "export data form", route = "export_data_form")

    object ExportDataSuccess :
        Destination("export data success", route = "export_data_success")

}
