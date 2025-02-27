/*
 * This file is part of ViaFabricPlus - https://github.com/FlorianMichael/ViaFabricPlus
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.viafabricplus.definition.c0_30;

import com.google.common.hash.Hashing;
import com.google.common.io.Resources;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import de.florianmichael.viafabricplus.util.ScreenUtil;
import net.raphimc.vialegacy.protocols.release.protocol1_3_1_2to1_2_4_5.providers.OldAuthProvider;

import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BetaCraftImpl {

    public static String getBetaCraftMpPass(final UserConnection user, final String username, final String serverIp, final int port) {
        try {
            final String server = InetAddress.getByName(serverIp).getHostAddress() + ":" + port;
            Via.getManager().getProviders().get(OldAuthProvider.class).sendAuthRequest(user, Hashing.sha1().hashBytes(server.getBytes()).toString());
            final String mppass = Resources.toString(new URL("http://api.betacraft.uk/getmppass.jsp?user=" + username + "&server=" + server), StandardCharsets.UTF_8);
            if (mppass.contains("FAILED") || mppass.contains("SERVER NOT FOUND")) return "0";
            return mppass;
        } catch (Throwable e) {
            ScreenUtil.crash("An unknown error occurred while authenticating with BetaCraft", e);
        }
        return "0";
    }
}
