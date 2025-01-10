package edu.mcc.tic_tac_toe.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Evan Stohlmann",
                        email = "esstohlmann@mccneb.edu",
                        url = "https://www.mccneb.edu/Community-Business/Workforce-Innovation-Division/Workforce-Development/Programs-and-Academies/Code-School"
                ),
                title = "Tic-Tac-Toe",
                description = "This is the doc site for the MCC Tic-Tac-Toe APIs",
                version = "1.0"
        )
)
public class OpenAPIConfig {
}
