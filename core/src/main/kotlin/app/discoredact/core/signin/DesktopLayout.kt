package app.discoredact.core.signin

/**
 * The sign-in page's desktop layout (OQ390): Discord draws its QR-code login only in its wide layout, so on request the
 * page is laid out at [WIDTH_CSS_PX] CSS pixels and scaled down to fit the screen. Nothing about the request changes
 * (same user agent, no script): only the size the page is given. Pure arithmetic, so the JVM tests cover it.
 */
object DesktopLayout {
    /** Wide enough for Discord's two-column login (form and QR code side by side). */
    const val WIDTH_CSS_PX = 1280

    /** The page's size in view pixels before scaling, and the scale that fits it into the container. */
    data class Fit(val widthPx: Int, val heightPx: Int, val scale: Float)

    /**
     * For a container of [containerWidthPx] by [containerHeightPx] view pixels at [density] view pixels per CSS pixel:
     * the page is [WIDTH_CSS_PX] wide, never narrower than the container, and as tall as the container once scaled.
     * Null for an empty container (nothing to lay out yet).
     */
    fun fit(containerWidthPx: Int, containerHeightPx: Int, density: Float): Fit? {
        if (containerWidthPx <= 0 || containerHeightPx <= 0 || !(density > 0f)) return null
        val widthPx = maxOf(containerWidthPx, Math.round(WIDTH_CSS_PX * density))
        val scale = containerWidthPx.toFloat() / widthPx
        val heightPx = Math.round(containerHeightPx / scale)
        return Fit(widthPx, heightPx, scale)
    }
}
