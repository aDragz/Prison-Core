package prisoncore.aDragz.Features.Logs.Features.Tokens;

import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.saveTokenTransactions;
import prisoncore.aDragz.Features.Logs.LogManagement.Tokens.storeTokenTransactions;

import java.io.IOException;

public class logsTokenResetFile {

    public static void resetTokensTransactions() throws IOException {
        storeTokenTransactions.transactions.clear();
        saveTokenTransactions.autoSaveTransactions();
    }
}
