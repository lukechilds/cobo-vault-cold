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

package com.cobo.coinlib.coins;

import androidx.annotation.NonNull;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.MainNetParams;

public abstract class AbsDeriver {
    public static AbsDeriver newInstance(@NonNull String coinCode) {
        try {
            Class clazz = Class.forName(CoinReflect.getCoinClassByCoinCode(coinCode) + "$Deriver");
            return (AbsDeriver) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static final NetworkParameters MAINNET = MainNetParams.get();

    protected DeterministicKey getAddrDeterministicKey(String accountXpub, int changeIndex, int addressIndex) {
        DeterministicKey account = DeterministicKey.deserializeB58(accountXpub, MAINNET);
        DeterministicKey change = HDKeyDerivation.deriveChildKey(account, changeIndex);
        return HDKeyDerivation.deriveChildKey(change, addressIndex);
    }

    protected DeterministicKey getDeterministicKey(String xPub) {
        return DeterministicKey.deserializeB58(xPub, MAINNET);
    }

    public abstract String derive(String xPubKey, int changeIndex, int addrIndex);

    public abstract String derive(String xPubKey);
}
