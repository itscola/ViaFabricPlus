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
package de.florianmichael.viafabricplus.information.impl;

import com.viaversion.viaversion.api.connection.UserConnection;
import de.florianmichael.viafabricplus.information.AbstractInformationGroup;
import de.florianmichael.vialoadingbase.model.ProtocolRange;
import net.raphimc.vialegacy.api.LegacyProtocolVersion;
import net.raphimc.vialegacy.protocols.release.protocol1_6_1to1_5_2.storage.EntityTracker;

import java.util.List;

public class V1_5_2Information extends AbstractInformationGroup {

    public V1_5_2Information() {
        super(ProtocolRange.andOlder(LegacyProtocolVersion.r1_5_2));
    }

    @Override
    public void applyInformation(UserConnection userConnection, List<String> output) {
        if (userConnection.has(EntityTracker.class)) {
            final int entities = userConnection.get(EntityTracker.class).getTrackedEntities().size();
            if (entities != 0) output.add("Entity Tracker: " + entities);
        }
    }
}
