package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return (year - other.year)*365 + (month - other.month)*31 + dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

operator fun MyDate.plus(timeInterval: TimeInterval) : MyDate {
    return addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) : MyDate {
    return addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.n)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(n: Int) : RepeatedTimeInterval{
    return RepeatedTimeInterval(this, n)
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val n: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var next : MyDate = start;

            override fun hasNext(): Boolean {
                return next <= endInclusive
            }

            override fun next(): MyDate {
                val restult = next
                next = next.nextDay();
                return restult;
            }
        }

    }
}
