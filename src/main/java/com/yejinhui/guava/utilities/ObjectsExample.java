package com.yejinhui.guava.utilities;

import com.google.common.collect.ComparisonChain;

import java.util.Calendar;
import java.util.Objects;

/**
 * @author ye.jinhui
 * @description
 * @program guava
 * @create 2020/2/7 22:52
 */
public class ObjectsExample {

    static class Guava implements Comparable<Guava> {
        private final String manufacturer;
        private final String version;
        private final Calendar releaseDate;

        public Guava(String manufacturer, String version, Calendar releaseDate) {
            this.manufacturer = manufacturer;
            this.version = version;
            this.releaseDate = releaseDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guava guava = (Guava) o;
            return Objects.equals(manufacturer, guava.manufacturer) &&
                    Objects.equals(version, guava.version) &&
                    Objects.equals(releaseDate, guava.releaseDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(manufacturer, version, releaseDate);
        }

        @Override
        public String toString() {
            return "Guava{" +
                    "manufacturer='" + manufacturer + '\'' +
                    ", version='" + version + '\'' +
                    ", releaseDate=" + releaseDate +
                    '}';
        }

        @Override
        public int compareTo(Guava o) {
            return ComparisonChain.start().compare(this.manufacturer, o.manufacturer)
                    .compare(this.version, o.version).compare(this.releaseDate, o.releaseDate).result();
        }
    }
}
