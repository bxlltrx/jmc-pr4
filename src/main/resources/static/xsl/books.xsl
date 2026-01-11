<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8" />

  <xsl:template match="/books">
    <html>
      <head><title>Books</title></head>
      <body>
        <h1>Books</h1>
        <table border="1" cellpadding="6">
          <tr>
            <th>ID</th><th>Title</th><th>ISBN</th><th>Year</th><th>Author</th><th>Links</th>
          </tr>
          <xsl:for-each select="book">
            <tr>
              <td><xsl:value-of select="id"/></td>
              <td><xsl:value-of select="title"/></td>
              <td><xsl:value-of select="isbn"/></td>
              <td><xsl:value-of select="publishedYear"/></td>
              <td>
                <xsl:value-of select="authorName"/>
                <xsl:text> (#</xsl:text><xsl:value-of select="authorId"/><xsl:text>)</xsl:text>
              </td>
              <td>
                <a>
                  <xsl:attribute name="href">/api/books/<xsl:value-of select="id"/></xsl:attribute>
                  view book (XML)
                </a>
                <xsl:text> | </xsl:text>
                <a>
                  <xsl:attribute name="href">/api/authors/<xsl:value-of select="authorId"/></xsl:attribute>
                  view author (XML)
                </a>
              </td>
            </tr>
          </xsl:for-each>
        </table>
        <p><a href="/api/authors">Authors (JSON)</a></p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
