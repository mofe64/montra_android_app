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

}
