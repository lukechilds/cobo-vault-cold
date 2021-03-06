/*
 * Copyright (c) 2020 Cobo
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
 * in the file COPYING.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cobo.coinlib.coin;

import com.cobo.coinlib.coins.LTC.Ltc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ALL")
public class LtcTest {
    @Test
    public void deriveAddress() {
        String[] addr = new String[] {
                "3DYRx8E2vK8KaXA9LJ21vV4wGL4hmRYmCL",
                "3Qg4Jb6GJM2vk4eDwiyouPQRAukVa5Mbk7",
                "3FnRAxvQm2qbAYSWoQnv2Jb1jqruYtFhMr",
                "3NAwgFJy5nuMpguT4RM1t2niQe4GDLbU1r",
                "3AQR2S4tpfDMPQzj44WC6rcHh5ZbYdBcWt",
                "3QkyucsqatrXJHgLoYK7WiHu8BQeBhjuXT",
                "3KngFdv9BNwYoAY7uawRs49R1dWwhJM5mD",
                "3PgjwKnwxgPzbPbTarLpxCx6WzYufC5DEB",
                "3PHP2LRaPoiJYkWt9q6Zf2KJZdonarZVzv",
                "3LARJepdkdgcByt7FrKyzBovraY8GHeXht",
                "3CAzK2RGXrCMoerQ1UFdmUHDyw5t4QcR33"
                };
        String pubKey = "xpub6CKt97v4gEsG4FG9E4hEotEUtjmW8rAvVcUJ4jsmdrB437WBZnK8gs8ktzaFQHe9i7NqzcAUkc5SeNXsVoYfVNxd1AwDgbw2up8UdMWq91B";
        for (int i = 0 ; i < addr.length; i++) {
            String address = new Ltc.Deriver().derive(pubKey,0,i);
            assertEquals(address,addr[i]);
        }
    }

}
