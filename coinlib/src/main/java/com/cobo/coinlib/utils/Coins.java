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

package com.cobo.coinlib.utils;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Coins {

    public static final Coin BTC = new Coin("bitcoin", "BTC", "Bitcoin", 0);
    public static final Coin BCH = new Coin("bitcoin_cash", "BCH", "Bitcoin Cash", 145);
    public static final Coin DASH = new Coin("dash", "DASH", "Dash", 5);
    public static final Coin LTC = new Coin("litecoin", "LTC", "Litecoin", 2);
    public static final Coin ETH = new Coin("ethereum", "ETH", "Ethereum", 60);
    public static final Coin ETC = new Coin("ethereum_classic", "ETC", "Ethereum Classic", 61);
    public static final Coin TRON = new Coin("tron", "TRON", "Tron", 195);
    public static final Coin DCR = new Coin("dcr", "DCR", "Dcr", 42);
    public static final Coin XZC = new Coin("zcoin", "XZC", "Zcoin", 136);
    public static final Coin XRP = new Coin("ripple", "XRP", "Ripple", 144);
    public static final Coin IOST = new Coin("iost", "IOST", "IOST", 291, CURVE.ED25519);
    public static final Coin EOS = new Coin("eos", "EOS", "EOS", 194);

    public static final List<Coin> SUPPORTED_COINS = Arrays.asList(
            BTC,
            ETC,
            ETH,
            BCH,
            DASH,
            LTC,
            TRON,
            DCR,
            XZC,
            XRP,
            IOST,
            EOS
    );

    public static boolean isCoinSupported(@NonNull String coinCode) {
        return SUPPORTED_COINS.stream().anyMatch(coin -> coin.coinCode.equals(coinCode));
    }

    public static boolean supportMultiSigner(@NonNull String coinCode) {
        switch (coinCode) {
            case "BTC":
            case "BCH":
            case "LTC":
            case "DASH":
                return true;
            default:
                return false;
        }
    }

    public static String coinCodeFromCoinId(String coinId) {
        Optional<Coin> coin = SUPPORTED_COINS.stream()
                .filter(c -> coinId.equals(c.coinId))
                .findFirst();
        return coin.isPresent() ? coin.get().coinCode : "";
    }

    public static String coinIdFromCoinCode(String coinCode) {
        Optional<Coin> coin = SUPPORTED_COINS.stream()
                .filter(c -> coinCode.equals(c.coinCode))
                .findFirst();
        return coin.isPresent() ? coin.get().coinId : "";
    }

    public static CURVE curveFromCoinCode(String coinCode) {
        Optional<Coin> coin = SUPPORTED_COINS.stream()
                .filter(c -> coinCode.equals(c.coinCode))
                .findFirst();
        return coin.isPresent() ? coin.get().curve : CURVE.SECP256K1;
    }

    public static String coinCodeOfIndex(int coinIndex) {
        Optional<Coin> coin = SUPPORTED_COINS.stream()
                .filter(c -> coinIndex == c.coinIndex)
                .findFirst();
        return coin.isPresent() ? coin.get().coinCode : "";
    }

    public static String coinNameOfCoinId(String coinId) {
        Optional<Coin> coin = SUPPORTED_COINS.stream()
                .filter(c -> coinId.equals(c.coinId))
                .findFirst();
        return coin.isPresent() ? coin.get().coinName() : "";
    }

    public static class Coin {
        private final String coinId;
        private final String coinCode;
        private final String coinName;
        private final int coinIndex;
        private final CURVE curve;


        public Coin(String coinId, String coinCode, String coinName, int coinIndex) {
            this(coinId, coinCode, coinName, coinIndex, CURVE.SECP256K1);
        }

        public Coin(String coinId, String coinCode, String coinName, int coinIndex, CURVE curve) {
            this.coinId = coinId;
            this.coinCode = coinCode;
            this.coinName = coinName;
            this.coinIndex = coinIndex;
            this.curve = curve;
        }

        public String
        coinId() {
            return coinId;
        }

        public String coinCode() {
            return coinCode;
        }

        public String coinName() {
            return coinName;
        }

        public int coinIndex() {
            return coinIndex;
        }

        public CURVE curve() {
            return curve;
        }
    }

    public enum CURVE {
        ED25519,
        SECP256K1,
        SECP256R1
    }

    public static int purposeNumber(String coinCode) {
        switch (coinCode) {
            case "BTC":
            case "XTN":
            case "LTC":
                return 49;
            default:
                return 44;
        }
    }

    public static boolean showPublicKey(String coinCode) {
        switch (coinCode) {
            case "EOS":
            case "IOST":
                return true;
            default:
                return false;
        }
    }

}
