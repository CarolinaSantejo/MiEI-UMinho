#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#include "clients.h"

/**
 * Initializes a new instance of Clients.
 */
Clients initClients() {
    Clients clients = g_tree_new_full((GCompareDataFunc)strcmp, NULL, g_free, g_free);
    return clients;
}

/**
 * Adds a client to an instance of Clients.
 */
bool addClient(Clients clients, char* clientID) {
    if(is_valid_client(clientID)) {
        bool* branchInfo = calloc(3, sizeof(bool));
        g_tree_insert(clients, g_strdup(clientID), branchInfo);
        return true;
    }
    return false;
}

/**
 * Frees all memory allocated to an instance of Clients.
 */
void destroyClients(Clients clients) {
    g_tree_destroy(clients);
}

/**
 * Checks if a client code is valid or not.
 */
bool is_valid_client(char* client) {
    if(strlen(client) != 5) return false;
    if(!isupper(*client)) return false;
    int num;
    if(sscanf(client + 1, "%d", &num) != EOF) {
        return num > 999 && num < 5001;
    }
    return false;
}

/**
 * Checks if a given client is in an instance of Clients.
 */
bool is_in_clients(Clients clients, char* clientID) {
    if(is_valid_client(clientID))
        return g_tree_lookup(clients,clientID) != NULL;
    return false;
}

/**
 * Updates the information of a given client stored in an instance of Clients.
 */
void updateClientBranchInfo(Clients clients, char* clientID, int branch) {
    bool* branchInfo = g_tree_lookup(clients, clientID);
    branchInfo[branch] = true;
}

/**
 * Checks whether a client made a purchase in a given branch.
 */
bool clientBoughtInBranch(Clients clients, char* clientID, int branch) {
    bool* branchInfo = g_tree_lookup(clients, clientID);
    if(!branchInfo) return false;
    return branchInfo[branch];
}