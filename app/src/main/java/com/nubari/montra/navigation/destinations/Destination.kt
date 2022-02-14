package com.nubari.montra.navigation.destinations

sealed class Destination(val name: String, val route: String) {
    object SignupDestination : Destination(name = "signup", route = "signup")
    object OnBoardingDestination : Destination(name = "onboarding", route = "onboarding")
    object LoginDestination : Destination(name = "login", route = "login")
    object ForgotPasswordDestination :
        Destination(name = "forgot password", route = "forgot_password")

    object VerificationDestination : Destination(name = "verification", route = "verification")

    object AccountSetupFormDestination :
        Destination(name = "set up account", route = "setup_account")

    object AccountSetupCompleteDestination :
        Destination(name = "account setup complete", route = "account_setup_complete")

    object NewTransaction : Destination(name = "new transaction", route = "new_transaction")

}
