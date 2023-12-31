package org.fjerp.serveur;

import java.util.Map;

import io.undertow.security.idm.Account;
import io.undertow.security.idm.Credential;
import io.undertow.security.idm.IdentityManager;

public class CustomIdentityManager implements IdentityManager
{
    private Map<String, char[]> users;

    // standard constructors
    public CustomIdentityManager(Map<String, char[]> users)
    {
        this.users = users;
    }

    @Override
    public Account verify(Account account)
    {
        return account;
    }

    @Override
    public Account verify(Credential credential)
    {
        return null;
    }

    @Override
    public Account verify(String id, Credential credential)
    {
        Account account = getAccount(id);
        if (
            account != null && verifyCredential(account, credential)
        )
        {
            return account;
        }
        return null;
    }

    private Account getAccount(String id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private boolean verifyCredential(Account account, Credential credential)
    {
        // TODO Auto-generated method stub
        return false;
    }
}