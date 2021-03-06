package com.github.zgynhqf.rafy4j.dbmigration.providers;

/**
 * 数据库标识符的处理器。
 */
public abstract class BaseDbIdentifierQuoter implements DbIdentifierQuoter {
    /**
     * 标识符被引用时的起始字符
     */
    public abstract char getQuoteStart();

    /**
     * 标识符被引用时的终止字符。
     * 默认与 getQuoteStart 相同。
     *
     * @see BaseDbIdentifierQuoter#getQuoteStart()
     */
    public char getQuoteEnd() {
        return this.getQuoteStart();
    }

    /**
     * 对于最终生成好的关键标识，在其外围添加相应的引用符。
     * 它与 IDbIdentifierQuoter.prepare(string) 方法的区别在于，后者可以对一个标识符中的某一部分使用。
     * 例如：PK_Table_Id 中的 Table、Id 都需要调用 prepare，而最终的 PK_Table_Id 则只需要调用 quote。
     *
     * @param identifier The identifier.
     * @return
     * @see DbIdentifierQuoter#prepare(String)
     */
    public final String quote(String identifier) {
        identifier = this.prepare(identifier);

        if (this.getQuoteStart() != Character.MIN_VALUE) {
            return this.getQuoteStart() + identifier + this.getQuoteEnd();
        }

        return identifier;
    }

    /**
     * 准备 SQL 中所使用到的任意一个关键标识（表、字段、外键、主键、约束名等）。
     *
     * @param identifier
     * @return
     */
    public String prepare(String identifier) {
        return identifier;
    }

    public static String limitIdentifier(String identifier) {
        return limitIdentifier(identifier, 30);
    }

    /**
     * 某些数据库对标识符的长度会有限制，例如 Oracle 中不能超过 30 个字符。
     * 这个方法可以把传入的字符串剪裁到 length 个字符，并尽量保持有用的信息。
     *
     * @param identifier The identifier.
     * @param length     The length.
     * @return
     */
    public static String limitIdentifier(String identifier, int length) {
        if (identifier.length() > length) {
            //var toCut = identifier.Length - length;

            //if (identifier.StartsWith("FK_"))
            //{
            //    identifier = identifier.Substring(3);
            //}

            ////保留 ID 字样
            //var newName = identifier.Replace("Id", "ID");

            ////从后面开始把多余的小写字母去除。
            //var list = newName.ToList();
            //for (int i = list.Count - 1; i >= 0 && toCut > 0; i--)
            //{
            //    var c = list[i];
            //    if (char.IsLower(c))
            //    {
            //        list.RemoveAt(i);
            //        toCut--;
            //    }
            //}
            ////如何还是太长，直接截取
            //for (int i = list.Count - 1; toCut > 0; i--)
            //{
            //    list.RemoveAt(i);
            //    toCut--;
            //}
            //newName = new string(list.ToArray());

            //return newName;

            //以上算法会导致一些缩写的名称重复。所以不如直接截取。这样，外层程序应该保证越在前面的字符，重要性越高。
            return identifier.substring(0, length);
        }

        return identifier;
    }
}